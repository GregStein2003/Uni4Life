package br.edu.unisinos.uni4life.service;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.NOT_FOUND;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.CONTEUDO_JA_CURTIDO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.CONTEUDO_JA_FAVORITADO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.CONTEUDO_NAO_ENCONTRADO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.IMAGEM_CONTEUDO_INVALIDA;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.LINK_CONTEUDO_INVALIDO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.USUARIO_NAO_ENCONTRADO;
import static br.edu.unisinos.uni4life.security.SecurityContextHelper.getUsuarioPrincipalId;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.domain.entity.ImageEntity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.InformacoesExtraConteudo;
import br.edu.unisinos.uni4life.dto.request.CadastraConteudoRequest;
import br.edu.unisinos.uni4life.dto.response.ConteudoResponse;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.mapper.ComentarioResponseMapper;
import br.edu.unisinos.uni4life.mapper.ConteudoResponseMapper;
import br.edu.unisinos.uni4life.mapper.entity.ConteudoEntityMapper;
import br.edu.unisinos.uni4life.mapper.entity.CurtidaEntityMapper;
import br.edu.unisinos.uni4life.mapper.entity.FavoritoEntityMapper;
import br.edu.unisinos.uni4life.repository.ConteudoRepository;
import br.edu.unisinos.uni4life.repository.CurtidaRepository;
import br.edu.unisinos.uni4life.repository.FavoritoRepository;
import br.edu.unisinos.uni4life.repository.UsuarioRepository;
import br.edu.unisinos.uni4life.service.support.MessageService;
import br.edu.unisinos.uni4life.service.support.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConteudoService {

    private static final ConteudoEntityMapper CONTEUDO_MAPPER = new ConteudoEntityMapper();

    // Support
    private final StorageService storageService;
    private final MessageService messageService;

    //Repository
    private final UsuarioRepository userRepository;
    private final ConteudoRepository repository;
    private final FavoritoRepository favoritoRepository;
    private final CurtidaRepository curtidaRepository;

    public ConteudoResponse consultar(final UUID idConteudo) {
        log.info("Consultando conteúdo com ID: {}", idConteudo);

        return repository.findById(idConteudo)
            .map(enitity -> new ConteudoResponseMapper().apply(enitity, getImagemBase64(enitity),
                construirInformacoesExtra(enitity)))
            .orElseThrow(() -> {
                log.warn("Conteúdo não encontrado com ID: {}", idConteudo);
                return new ClientErrorException(NOT_FOUND, messageService.get(CONTEUDO_NAO_ENCONTRADO));
            });
    }

    public Page<ConteudoResponse> consultar(final Pageable paginacao) {
        return this.consultar(getUsuarioPrincipalId(), paginacao);
    }

    public Page<ConteudoResponse> consultar(final UUID idUsuario, final Pageable paginacao) {
        log.info("Consultando conteúdo do usuário: {}", idUsuario);

        return repository.findByAutorId(idUsuario, paginacao)
            .map(enitity -> new ConteudoResponseMapper().apply(enitity, getImagemBase64(enitity),
                construirInformacoesExtra(enitity)));
    }

    public Page<ConteudoResponse> consultarConteudosFavoritados(final Pageable paginacao) {
        log.info("Consultando conteúdos favoritados pelo usuário: {}", getUsuarioPrincipalId());

        return repository.findConteudoFavoritado(getUsuarioPrincipalId(), paginacao)
            .map(enitity -> new ConteudoResponseMapper().apply(enitity, getImagemBase64(enitity),
                construirInformacoesExtra(enitity, true, isCurtido(enitity))));
    }

    public Page<ConteudoResponse> consultarConteudosCurtidos(final Pageable paginacao) {
        log.info("Consultando conteúdos curtidos pelo usuário: {}", getUsuarioPrincipalId());

        return repository.findConteudoCurtido(getUsuarioPrincipalId(), paginacao)
            .map(enitity -> new ConteudoResponseMapper().apply(enitity, getImagemBase64(enitity),
                construirInformacoesExtra(enitity, isFavorito(enitity), true)));
    }

    public Page<ConteudoResponse> consultarConteudosSeguidos(final Pageable paginacao) {
        log.info("Consultando conteúdos seguidos pelo usuário: {}", getUsuarioPrincipalId());

        return repository.findConteudoSeguido(getUsuarioPrincipalId(), paginacao)
            .map(enitity -> new ConteudoResponseMapper().apply(enitity, getImagemBase64(enitity),
                construirInformacoesExtra(enitity)));
    }

    @Transactional
    public ConteudoResponse cadastrar(final CadastraConteudoRequest request) {
        log.info("Realizando cadastro do conteudo: {}", request);

        final UsuarioEntity autor = getUsuario(getUsuarioPrincipalId());

        if (validaLinkConteudo(request)) {
            log.warn("Link não informado ou inválido.");
            throw new ClientErrorException(VALIDATION, messageService.get(LINK_CONTEUDO_INVALIDO), "link");
        }

        final String arquivo;

        try {
            arquivo = storageService.salvar(request.getImagem());
        } catch (final Exception exception) {
            log.debug("Erro para salvar imagem: {}", exception.getMessage());
            throw new ClientErrorException(VALIDATION, messageService.get(IMAGEM_CONTEUDO_INVALIDA), "imagem");
        }

        final ConteudoEnitity enitity = repository.save(CONTEUDO_MAPPER.apply(request, autor, arquivo));

        return new ConteudoResponseMapper().apply(enitity, request.getImagem(),
            construirInformacoesExtra(enitity, false, false));
    }

    @Transactional
    public ConteudoResponse favoritar(final UUID idConteudo) {
        log.info("Favoritando o conteúdo com id: {}", idConteudo);

        final UsuarioEntity usuario = getUsuario(getUsuarioPrincipalId());
        final ConteudoEnitity conteudo = getConteudo(idConteudo);

        if (favoritoRepository.existsByUsuarioAndConteudo(usuario, conteudo)) {
            log.warn("Conteúdo já favoritado pelo usuário");
            throw new ClientErrorException(VALIDATION, messageService.get(CONTEUDO_JA_FAVORITADO));
        }

        favoritoRepository.save(new FavoritoEntityMapper().apply(usuario, conteudo));

        return new ConteudoResponseMapper().apply(conteudo, getImagemBase64(conteudo),
            construirInformacoesExtra(conteudo, true, isCurtido(conteudo)));
    }

    @Transactional
    public ConteudoResponse desfavoritar(final UUID idConteudo) {
        log.info("Desfavoritando o conteúdo com id: {}", idConteudo);
        final UUID idUsuario = getUsuarioPrincipalId();
        final ConteudoEnitity conteudo = getConteudo(idConteudo);

        favoritoRepository.deleteByUsuarioIdAndConteudo(idUsuario, conteudo);

        return new ConteudoResponseMapper().apply(conteudo, getImagemBase64(conteudo),
            construirInformacoesExtra(conteudo, false, isCurtido(conteudo)));
    }

    @Transactional
    public ConteudoResponse curtir(final UUID idConteudo) {
        log.info("Curtindo o conteúdo com id: {}", idConteudo);

        final UsuarioEntity usuario = getUsuario(getUsuarioPrincipalId());
        final ConteudoEnitity conteudo = getConteudo(idConteudo);

        if (curtidaRepository.existsByUsuarioAndConteudo(usuario, conteudo)) {
            log.warn("Conteúdo já curtido pelo usuário");
            throw new ClientErrorException(VALIDATION, messageService.get(CONTEUDO_JA_CURTIDO));
        }

        curtidaRepository.save(new CurtidaEntityMapper().apply(usuario, conteudo));

        return new ConteudoResponseMapper().apply(conteudo, getImagemBase64(conteudo),
            construirInformacoesExtra(conteudo, isFavorito(conteudo), true));
    }

    @Transactional
    public ConteudoResponse descurtir(final UUID idConteudo) {
        log.info("Descurtindo o conteúdo com id: {}", idConteudo);
        final UUID idUsuario = getUsuarioPrincipalId();
        final ConteudoEnitity conteudo = getConteudo(idConteudo);

        curtidaRepository.deleteByUsuarioIdAndConteudo(idUsuario, conteudo);

        return new ConteudoResponseMapper().apply(conteudo, getImagemBase64(conteudo),
            construirInformacoesExtra(conteudo, isFavorito(conteudo), false));
    }

    private InformacoesExtraConteudo construirInformacoesExtra(final ConteudoEnitity conteudo) {
        return this.construirInformacoesExtra(conteudo, isFavorito(conteudo), isCurtido(conteudo));
    }

    private InformacoesExtraConteudo construirInformacoesExtra(final ConteudoEnitity conteudo,
        final boolean isFavorito, final boolean isCurtido) {
        return InformacoesExtraConteudo.builder()
            .imagemAutorBase64(getImagemBase64(conteudo.getAutor()))
            .isFavorito(isFavorito)
            .isCurtido(isCurtido)
            .comentarios(ofNullable(conteudo.getComentarios())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(comentario -> new ComentarioResponseMapper()
                    .apply(comentario, getImagemBase64(comentario.getAutor())))
                .collect(Collectors.toList()))
            .build();
    }

    private boolean validaLinkConteudo(final CadastraConteudoRequest request) {
        return request.getTipoConteudo().isPrecisaLink() && isBlank(request.getLink());
    }

    private boolean isFavorito(final ConteudoEnitity conteudo) {
        return favoritoRepository.existsByUsuarioIdAndConteudo(getUsuarioPrincipalId(), conteudo);
    }

    private boolean isFavorito(final UUID idUsuario, final ConteudoEnitity conteudo) {
        return favoritoRepository.existsByUsuarioIdAndConteudo(idUsuario, conteudo);
    }

    private boolean isCurtido(final ConteudoEnitity conteudo) {
        return curtidaRepository.existsByUsuarioIdAndConteudo(getUsuarioPrincipalId(), conteudo);
    }

    private boolean isCurtido(final UUID idUsuario, final ConteudoEnitity conteudo) {
        return curtidaRepository.existsByUsuarioIdAndConteudo(idUsuario, conteudo);
    }

    private UsuarioEntity getUsuario(final UUID idUsuario) {
        return userRepository.findById(idUsuario)
            .orElseThrow(() -> {
                log.warn("Usuário não encontrado: {}", idUsuario);
                return new ClientErrorException(NOT_FOUND, messageService.get(USUARIO_NAO_ENCONTRADO));
            });
    }

    private ConteudoEnitity getConteudo(final UUID idConteudo) {
        return repository.findById(idConteudo)
            .orElseThrow(() -> {
                log.warn("Conteúdo não encontrado: {}", idConteudo);
                return new ClientErrorException(NOT_FOUND, messageService.get(USUARIO_NAO_ENCONTRADO));
            });
    }

    private String getImagemBase64(final ImageEntity entity) {
        final String nomeImagem = ofNullable(entity)
            .map(ImageEntity::getImagem)
            .orElse(null);

        return storageService.consultar(nomeImagem);
    }
}
