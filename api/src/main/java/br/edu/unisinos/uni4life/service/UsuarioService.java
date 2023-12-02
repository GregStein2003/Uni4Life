package br.edu.unisinos.uni4life.service;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.BUSINESS;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.NOT_FOUND;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.EMAIL_CADASTRADO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.IMAGEM_USUARIO_INVALIDA;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.REQUISICAO_INVALIDA;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.USUARIO_NAO_ENCONTRADO;
import static br.edu.unisinos.uni4life.security.SecurityContextHelper.getUsuarioPrincipalId;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.AtualizaUsuarioRequest;
import br.edu.unisinos.uni4life.dto.request.CadastraUsuarioRequest;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.mapper.entity.UsuarioEntityMapper;
import br.edu.unisinos.uni4life.mapper.UsuarioResponseMapper;
import br.edu.unisinos.uni4life.repository.SeguidorRepository;
import br.edu.unisinos.uni4life.repository.UsuarioRepository;
import br.edu.unisinos.uni4life.service.support.AtualizaEntityService;
import br.edu.unisinos.uni4life.service.support.MessageService;
import br.edu.unisinos.uni4life.service.support.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final MessageService messageService;
    private final StorageService storageService;
    private final AtualizaEntityService atualizaEntityService;
    private final UsuarioRepository repository;
    private final SeguidorRepository seguidorRepository;

    public UsuarioResponse consultar(final UUID idUsuario) {
        log.info("Consultando usuário com ID: {}", idUsuario);

        final UUID idConsulta = nonNull(idUsuario) ? idUsuario : getUsuarioPrincipalId();

        return repository.findById(idConsulta)
            .map(entity -> new UsuarioResponseMapper().apply(entity, getImagemBase64(entity)))
            .orElseThrow(() -> {
                log.warn("Usuário não encontrado com ID: {}", idConsulta);
                return new ClientErrorException(NOT_FOUND, messageService.get(USUARIO_NAO_ENCONTRADO));
            });
    }

    public Page<UsuarioResponse> consultarUsuariosSeguidos(final Pageable paginacao) {
        log.info("Consultando os usuários que usuário. ID : {} esta seguindo", getUsuarioPrincipalId());

        return seguidorRepository.findAllSeguidosByIdSeguidor(getUsuarioPrincipalId(), paginacao)
            .map(seguidorEntity -> new UsuarioResponseMapper().apply(seguidorEntity.getSeguido(),
                getImagemBase64(seguidorEntity.getSeguido()), seguidorEntity.getDataInicioRelacionamento()));
    }

    public Page<UsuarioResponse> consultarUsuariosParaSeguir(final Pageable paginacao) {
        final UUID idUsuarioLogado = getUsuarioPrincipalId();
        log.info("Consultando os usuários que usuário. ID : {} não estao seguindo", idUsuarioLogado);

        return repository.findUsuariosToFollow(idUsuarioLogado, paginacao)
            .map(entity -> {
                final boolean isSeguido = segueUsuario(idUsuarioLogado, entity);
                return new UsuarioResponseMapper().apply(entity, isSeguido, getImagemBase64(entity));
            });
    }

    @Transactional
    public UsuarioResponse cadastrar(final CadastraUsuarioRequest request) {
        log.info("Realizando o cadastro do usuário: {}", request);

        if (repository.existsByEmail(request.getEmail())) {
            log.warn("Esse endereço de email já foi cadastrado anteriormente.");
            throw new ClientErrorException(BUSINESS, messageService.get(EMAIL_CADASTRADO), "email");
        }

        final String arquivo = salvarImagem(request.getImagem());

        final UsuarioEntityMapper mapper = new UsuarioEntityMapper();
        final UsuarioEntity entity = repository.save(mapper.apply(request, arquivo));

        return new UsuarioResponseMapper().apply(entity, request.getImagem());
    }

    @Transactional
    public UsuarioResponse atualizar(final AtualizaUsuarioRequest request) {
        if (isNull(request)) {
            log.warn("Requisição inválida");
            throw new ClientErrorException(VALIDATION, messageService.get(REQUISICAO_INVALIDA));
        }

        final UsuarioEntity entity = repository.findById(getUsuarioPrincipalId())
            .orElseThrow(() -> {
                log.warn("Usuário com ID: {} não econtrado", getUsuarioPrincipalId());
                return new ClientErrorException(VALIDATION, messageService.get(USUARIO_NAO_ENCONTRADO));
            });

        ofNullable(request.getEmail())
            .filter(email -> repository.existsByEmailAndIdIsNot(email, getUsuarioPrincipalId()))
            .ifPresent(email -> {
                log.warn("Esse endereço de email já foi cadastrado anteriormente.");
                throw new ClientErrorException(BUSINESS, messageService.get(EMAIL_CADASTRADO), "email");
            });

        final UsuarioEntity novaEntity = atualizaEntityService.atualizar(request, entity);

        repository.save(novaEntity);

        return new UsuarioResponseMapper().apply(novaEntity, getImagemBase64(novaEntity));
    }


    // TODO: Ver se é possível validar se é possível fazer em uma consula
    private boolean segueUsuario(final UUID idSeguidor, final UsuarioEntity seguido) {
        return seguidorRepository.existsSeguidorEntityBySeguidorIdAndSeguido(idSeguidor, seguido);
    }

    private String getImagemBase64(final UsuarioEntity usuario) {
        final String nomeImagem = ofNullable(usuario)
            .map(UsuarioEntity::getImagem)
            .orElse(null);

        return storageService.consultar(nomeImagem);
    }

    private String salvarImagem(final String imagemBase64) {
        try {
            return storageService.salvar(imagemBase64);
        } catch (final Exception exception) {
            log.debug("Erro para salvar imagem: {}", exception.getMessage());
            throw new ClientErrorException(VALIDATION, messageService.get(IMAGEM_USUARIO_INVALIDA), "imagem");
        }
    }
}
