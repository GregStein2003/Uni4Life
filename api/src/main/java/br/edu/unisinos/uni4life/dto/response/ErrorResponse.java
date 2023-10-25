package br.edu.unisinos.uni4life.dto.response;


import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.INTERNAL_ERROR;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.BooleanUtils.negate;

import java.io.Serializable;
import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;

import br.edu.unisinos.uni4life.domain.enumeration.ErrorType;
import br.edu.unisinos.uni4life.exception.AbstractErrorException;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 5510253969993856060L;

    private ErrorType errorType;
    private String message;
    private Map<String, String> details;

    public static ErrorResponse build(final Exception exception) {

        if (exception instanceof AbstractErrorException) {
            return build(exception);
        }

        if (exception instanceof HttpMessageNotReadableException) {
            return build(exception);
        }

        return buildDefault();
    }

    public static ErrorResponse build(final AbstractErrorException ex) {
        return ErrorResponse.builder()
            .errorType(ex.getErrorType())
            .message(ex.getMessage())
            .details(negate(ex.getDetails().isEmpty()) ? ex.getDetails() : emptyMap())
            .build();
    }

    public static ErrorResponse build(final HttpMessageNotReadableException ex) {
        return ErrorResponse.builder()
            .errorType(VALIDATION)
            .message(getMessageFromCause(ex.getCause()))
            .details(getDetailsFromCause(ex.getCause()))
            .build();
    }

    public static ErrorResponse buildDefault() {
        return ErrorResponse.builder()
            .errorType(INTERNAL_ERROR)
            .message("Falha inesperada.")
            .build();
    }

    private static String getMessageFromCause(final Throwable throwable) {
        return ofNullable(throwable)
            .map(Throwable::getCause)
            .filter(ClientErrorException.class::isInstance)
            .map(Throwable::getMessage)
            .orElse("Requisição inválida.");
    }

    private static Map<String, String> getDetailsFromCause(final Throwable throwable) {
        return ofNullable(throwable)
            .map(Throwable::getCause)
            .filter(ClientErrorException.class::isInstance)
            .map(ClientErrorException.class::cast)
            .map(ClientErrorException::getDetails)
            .orElse(emptyMap());
    }
}
