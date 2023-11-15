package br.edu.unisinos.uni4life.service;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.BUSINESS;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.NOT_FOUND;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.EMAIL_CADASTRADO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.IMAGEM_USUARIO_INVALIDA;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.USUARIO_NAO_ENCONTRADO;
import static br.edu.unisinos.uni4life.security.SecurityContextHelper.getUsuarioPrincipalId;
import static java.util.Optional.ofNullable;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.CadastraUsuarioRequest;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.mapper.UsuarioEntityMapper;
import br.edu.unisinos.uni4life.mapper.UsuarioResponseMapper;
import br.edu.unisinos.uni4life.repository.SeguidorRepository;
import br.edu.unisinos.uni4life.repository.UsuarioRepository;
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
    private final UsuarioRepository repository;
    private final SeguidorRepository seguidorRepository;

    public UsuarioResponse consultar(final UUID idUsuario) {
        log.info("Consultando usuário com ID: {}", idUsuario);

        return repository.findById(idUsuario)
            .map(entity -> new UsuarioResponseMapper().apply(entity, getImagemBase64(entity)))
            .orElseThrow(() -> {
                log.warn("Usuário não encontrado com ID: {}", idUsuario);
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
        log.info("Consultando os usuários que usuário. ID : {} não estao seguindo", getUsuarioPrincipalId());

        return repository.findUsuariosToFollow(getUsuarioPrincipalId(), paginacao)
            .map(entity -> {
                final UsuarioEntity usuario = (UsuarioEntity) entity.get("usuario");
                final boolean isSeguido = (boolean) entity.get("seguido");
                return new UsuarioResponseMapper().apply(usuario, isSeguido, getImagemBase64(usuario));
            });
    }

    @Transactional
    public UsuarioResponse cadastrar(final CadastraUsuarioRequest request) {
        log.info("Realizando o cadastro do usuário: {}", request);

        if (repository.existsByEmail(request.getEmail())) {
            log.warn("Esse endereço de email já foi cadastrado anteriormente.");
            throw new ClientErrorException(BUSINESS, messageService.get(EMAIL_CADASTRADO), "email");
        }

        final String arquivo;

        try {
            arquivo = storageService.salvar(request.getImagem());
        } catch (final ClientErrorException exception) {
            log.debug("Erro para salvar imagem: {}", exception.getMessage());
            throw new ClientErrorException(VALIDATION, messageService.get(IMAGEM_USUARIO_INVALIDA));
        }

        final UsuarioEntityMapper mapper = new UsuarioEntityMapper();
        final UsuarioEntity entity = repository.save(mapper.apply(request, arquivo));

        return new UsuarioResponseMapper().apply(entity, request.getImagem());
    }

    private String getImagemBase64(final UsuarioEntity usuario) {
        final String nomeImagem = ofNullable(usuario)
            .map(UsuarioEntity::getImagem)
            .orElse(null);

        return storageService.consultar(nomeImagem);
    }
}
