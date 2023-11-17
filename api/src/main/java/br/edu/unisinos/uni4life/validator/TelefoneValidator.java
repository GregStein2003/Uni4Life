package br.edu.unisinos.uni4life.validator;

import static br.edu.unisinos.uni4life.domain.Padroes.TELEFONE_PADRAO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.TELEFONE_INVALIDO;

import org.springframework.stereotype.Component;

import br.edu.unisinos.uni4life.domain.enumeration.Message;
import br.edu.unisinos.uni4life.service.support.MessageService;

@Component
public class TelefoneValidator extends AbstractPatternValidator {

    public TelefoneValidator(final MessageService messageService) {
        super(messageService);
    }

    @Override
    protected String getPattern() {
        return TELEFONE_PADRAO;
    }

    @Override
    protected Message getErrorMessage() {
        return TELEFONE_INVALIDO;
    }

    @Override
    protected String validationName() {
        return "Telefone";
    }

    @Override
    protected String getCampo() {
        return "telefone";
    }
}
