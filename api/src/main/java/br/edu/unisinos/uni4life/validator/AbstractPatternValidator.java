package br.edu.unisinos.uni4life.validator;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.unisinos.uni4life.domain.enumeration.Message;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
abstract class AbstractPatternValidator implements Consumer<String> {

    private final MessageService messageService;

    @Override
    public void accept(final String str) {
        final Pattern pattern = Pattern.compile(getPattern());
        final Matcher matcher = pattern.matcher(str);

        if (isFalse(matcher.find())) {
            log.warn("Texto informado não bate padrão de: {}", validationName());
            throw new ClientErrorException(VALIDATION, messageService.get(getErrorMessage()), getCampo());
        }

    }

    protected abstract String getPattern();

    protected abstract Message getErrorMessage();

    protected abstract String validationName();

    protected String getCampo() {
        return null;
    }
}
