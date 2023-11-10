package br.edu.unisinos.uni4life.service;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.BUSINESS;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.NOT_FOUND;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.NAO_PERMITIDO_SEGUIR_VOCE;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.USUARIIO_JA_SEGUIDO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.USUARIO_NAO_ENCONTRADO;
import static br.edu.unisinos.uni4life.security.SecurityContextHelper.getUsuarioPrincipalId;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.mapper.SeguidorEntityMapper;
import br.edu.unisinos.uni4life.mapper.UsuarioResponseMapper;
import br.edu.unisinos.uni4life.repository.SeguidorRepository;
import br.edu.unisinos.uni4life.repository.UsuarioRepository;
import br.edu.unisinos.uni4life.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class SeguidoresService {

    private final MessageService messageService;
    private final SeguidorRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Page<UsuarioResponse> consultarSeguidores(final Pageable paginacao) {
        log.info("Consultando os seguidores do usuário: {}", getUsuarioPrincipalId());

        return repository.findAllSeguidoresByIdSeguido(getUsuarioPrincipalId(), paginacao)
            .map(seguidorEntity -> new UsuarioResponseMapper()
                .apply(seguidorEntity.getSeguidor(), seguidorEntity.getDataInicioRelacionamento()));
    }

    @Transactional
    public void seguir(final UUID idSeguido) {
        final UsuarioEntity seguido = getUsuario(idSeguido);
        final UsuarioEntity seguidor = getUsuario(getUsuarioPrincipalId());

        if (seguido == seguidor) {
            log.warn("Não é permitido seguir a você mesmo");
            throw new ClientErrorException(BUSINESS, messageService.get(NAO_PERMITIDO_SEGUIR_VOCE));
        }

        if (isSeguindo(seguido, seguidor)) {
            log.warn("Usuário autenticado já esta seguindo o usuário: {}", idSeguido);
            throw new ClientErrorException(BUSINESS, messageService.get(USUARIIO_JA_SEGUIDO));
        }

        repository.save(new SeguidorEntityMapper().apply(seguido, seguidor));
        atualizarUsuario(1L, idSeguido);
    }

    @Transactional
    public void removerSeguidor(final UUID idSeguido) {
        final UUID idSeguidor = getUsuarioPrincipalId();

        final long registrosDeletados = repository.delete(idSeguido, idSeguidor);

        if (registrosDeletados > 0) {
            atualizarUsuario(-registrosDeletados, idSeguido);
        }
    }

    public boolean isSeguindo(final UsuarioEntity seguido, final UsuarioEntity seguidor) {
        return repository.existsSeguidorEntityBySeguidorAndSeguido(seguidor, seguido);
    }


    // TODO: Pensar no uso de triggers no banco
    private void atualizarUsuario(final Long quantidadeSeguidores, final UUID idSeguido) {
        usuarioRepository.updateQuantidadeSeguidoresById(quantidadeSeguidores, idSeguido);
    }

    private UsuarioEntity getUsuario(final UUID idUsuario) {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> {
                log.warn("Usuário não com id: {}", idUsuario);
                return new ClientErrorException(NOT_FOUND, messageService.get(USUARIO_NAO_ENCONTRADO));
            });
    }
}
