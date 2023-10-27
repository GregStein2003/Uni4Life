package br.edu.unisinos.uni4life.service;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.NOT_FOUND;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.USUARIO_NAO_ENCONTRADO;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.CadastraUsuarioRequest;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.mapper.UsuarioEntityMapper;
import br.edu.unisinos.uni4life.mapper.UsuarioResponseMapper;
import br.edu.unisinos.uni4life.repository.UsuarioRepository;
import br.edu.unisinos.uni4life.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final UsuarioEntityMapper USUARIO_MAPPER = new UsuarioEntityMapper();

    private final MessageService messageService;
    private final UsuarioRepository repository;

    public UsuarioResponse consultar(final UUID idUsuario) {
        return repository.findById(idUsuario)
            .map(new UsuarioResponseMapper())
            .orElseThrow(() -> new ClientErrorException(NOT_FOUND, messageService.get(USUARIO_NAO_ENCONTRADO)));
    }

    @Transactional
    public UsuarioResponse cadastrar(final CadastraUsuarioRequest request) {
        log.info("Realizando o cadastro do usuário: " + request);

        final UsuarioEntity entity = repository.save(USUARIO_MAPPER.apply(request));

        return new UsuarioResponseMapper().apply(entity);
    }
}
