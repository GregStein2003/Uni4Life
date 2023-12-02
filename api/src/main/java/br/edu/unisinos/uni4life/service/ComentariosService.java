package br.edu.unisinos.uni4life.service;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.BUSINESS;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.NOT_FOUND;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.COMENTARIO_NAO_ENCONTRADO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.USUARIO_NAO_AUTOR_COMENTARIO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.USUARIO_NAO_ENCONTRADO;
import static br.edu.unisinos.uni4life.domain.enumeration.usuario.Segmento.ADMINISTRADOR;
import static br.edu.unisinos.uni4life.security.SecurityContextHelper.getUsuarioPrincipalId;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.entity.ComentarioEntity;
import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.domain.entity.ImageEntity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.CadastraComentarioRequest;
import br.edu.unisinos.uni4life.dto.response.ComentarioResponse;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.mapper.ComentarioResponseMapper;
import br.edu.unisinos.uni4life.mapper.entity.ComentarioEntityMapper;
import br.edu.unisinos.uni4life.repository.ComentariosRepository;
import br.edu.unisinos.uni4life.repository.ConteudoRepository;
import br.edu.unisinos.uni4life.repository.UsuarioRepository;
import br.edu.unisinos.uni4life.service.support.MessageService;
import br.edu.unisinos.uni4life.service.support.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComentariosService {

    private final StorageService storageService;
    private final MessageService messageService;

    private final ComentariosRepository repository;
    private final ConteudoRepository conteudoRepository;
    private final UsuarioRepository usuarioRepository;

    public Page<ComentarioResponse> consultar(final Pageable paginacao) {
        log.info("Consultando os comentários do usuário: {}", getUsuarioPrincipalId());

        return repository.findByAutorId(getUsuarioPrincipalId(), paginacao)
            .map(entity -> new ComentarioResponseMapper().apply(entity,
                getImagemBase64(entity.getAutor()), true));
    }


    public Page<ComentarioResponse> consultar(final UUID idConteudo, final Pageable paginacao) {
        log.info("Consultando os comentários do conteudo: {}", idConteudo);

        return repository.findByConteudoId(idConteudo, paginacao)
            .map(entity -> new ComentarioResponseMapper().apply(entity,
                getImagemBase64(entity.getAutor())));
    }

    @Transactional
    public ComentarioResponse cadastrar(final CadastraComentarioRequest request) {
        log.info("Realizando cadastro de um comentário: {}", request);

        final UsuarioEntity autor = getUsuario(getUsuarioPrincipalId());
        final ConteudoEnitity conteudo = getConteudo(request.getIdConteudo());

        final ComentarioEntity entity = repository.save(new ComentarioEntityMapper().apply(request, autor,
            conteudo));

        return new ComentarioResponseMapper().apply(entity, getImagemBase64(entity.getAutor()));
    }

    @Transactional
    public ComentarioResponse remover(final UUID idComentario) {
        final UsuarioEntity autor = getUsuario(getUsuarioPrincipalId());
        final ComentarioEntity comentario = getComentario(idComentario);

        if (isFalse(ADMINISTRADOR == autor.getSegmento())) {
            log.warn("Usuário não é um administrador, validando se comentário {} é dele", idComentario);

            if (isFalse(repository.existsByIdAndAutor(idComentario, autor))) {
                log.warn("Usuário precisa ser autor do coméntario para remover");
                throw new ClientErrorException(BUSINESS, messageService.get(USUARIO_NAO_AUTOR_COMENTARIO));
            }
        }

        repository.delete(comentario);

        return new ComentarioResponseMapper().apply(comentario, getImagemBase64(comentario.getAutor()));
    }

    private ComentarioEntity getComentario(final UUID idComentario) {
        return repository.findById(idComentario)
            .orElseThrow(() -> {
                log.warn("Comentário não encontrado: {}", idComentario);
                return new ClientErrorException(NOT_FOUND, messageService.get(COMENTARIO_NAO_ENCONTRADO));
            });
    }

    private UsuarioEntity getUsuario(final UUID idUsuario) {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> {
                log.warn("Usuário não encontrado: {}", idUsuario);
                return new ClientErrorException(NOT_FOUND, messageService.get(USUARIO_NAO_ENCONTRADO));
            });
    }


    private ConteudoEnitity getConteudo(final UUID idConteudo) {
        return conteudoRepository.findById(idConteudo)
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
