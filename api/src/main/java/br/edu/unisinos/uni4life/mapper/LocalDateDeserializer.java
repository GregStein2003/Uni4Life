package br.edu.unisinos.uni4life.mapper;

import static br.edu.unisinos.uni4life.domain.enumeration.CamposData.retornaMensagemErro;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import br.edu.unisinos.uni4life.domain.enumeration.CamposData;
import br.edu.unisinos.uni4life.domain.enumeration.ErrorType;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.service.support.MessageService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private final MessageService messageService;


    @Override
    public LocalDate deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {

        final String campo = jsonParser.getCurrentName();
        final String date = jsonParser.getText();

        if (isBlank(date)) {
            return null;
        }

        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
        } catch (final DateTimeException exception) {
            final String mensagem = messageService.get(retornaMensagemErro(campo));
            throw new ClientErrorException(VALIDATION, mensagem);
        }

    }
}
