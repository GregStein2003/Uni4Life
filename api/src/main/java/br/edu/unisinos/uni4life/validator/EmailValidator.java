package br.edu.unisinos.uni4life.validator;

import static br.edu.unisinos.uni4life.domain.Padroes.EMAIL_PADRAO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.EMAIL_INVALIDO;

import org.springframework.stereotype.Component;

import br.edu.unisinos.uni4life.domain.enumeration.Message;
import br.edu.unisinos.uni4life.service.support.MessageService;

@Component
public class EmailValidator extends AbstractPatternValidator {

    public EmailValidator(final MessageService messageService) {
        super(messageService);
    }

    @Override
    protected String getPattern() {
        return EMAIL_PADRAO;
    }

    @Override
    protected Message getErrorMessage() {
        return EMAIL_INVALIDO;
    }

    @Override
    protected String validationName() {
        return "Email";
    }

    @Override
    protected String getCampo() {
        return "email";
    }
}
