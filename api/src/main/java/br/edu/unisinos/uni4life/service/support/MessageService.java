package br.edu.unisinos.uni4life.service.support;

import static java.util.Objects.isNull;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.enumeration.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    private final Locale pt_BR = new Locale("pt", "BR");

    public String get(final Message mensagem, final Object... args) {

        if (isNull(mensagem)) {
            return "";
        }

        return messageSource.getMessage(mensagem.getMessage(), args, pt_BR);
    }
}
