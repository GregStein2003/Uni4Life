package br.edu.unisinos.uni4life.service;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.NOT_IMPLEMENTED;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.SERVICO_NAO_IMPLEMENADO;

import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.exception.ServerErrorException;
import br.edu.unisinos.uni4life.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final MessageService messageService;

    public String consultar(final Long id) {
        log.info("Consultando usuario: {}", id);
        throw new ServerErrorException(NOT_IMPLEMENTED, messageService.get(SERVICO_NAO_IMPLEMENADO));
    }
}
