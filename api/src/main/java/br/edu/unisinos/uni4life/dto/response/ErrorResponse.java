package br.edu.unisinos.uni4life.dto.response;


import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.INTERNAL_ERROR;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static java.lang.String.format;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.BooleanUtils.negate;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

        if (exception instanceof MethodArgumentNotValidException) {
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

    public static ErrorResponse build(final MethodArgumentNotValidException ex) {
        final List<String> erros = getErrors(ex);
        final String message = erros.stream()
            .findFirst()
            .orElse("Requisição inválida");

        return ErrorResponse.builder()
            .errorType(VALIDATION)
            .message(message)
            .details(emptyMap())
            .build();
    }

    public static ErrorResponse build(final MissingServletRequestParameterException ex) {
        final String parametro = ex.getParameterName();

        return ErrorResponse.builder()
            .errorType(VALIDATION)
            .message(format("Parâmetro '%s' inválido ou não informado.", parametro))
            .details(emptyMap())
            .build();
    }

    public static ErrorResponse build(final MethodArgumentTypeMismatchException ex) {
        final String parametro = ex.getName();

        return ErrorResponse.builder()
            .errorType(VALIDATION)
            .message(format("Parâmetro '%s' inválido ou não informado.", parametro))
            .details(emptyMap())
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
            .message("Não foi possível realizar operação, tente novamente mais tarde.")
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

    private static List<String> getErrors(final MethodArgumentNotValidException exception) {
        return ofNullable(exception)
            .map(BindException::getBindingResult)
            .map(Errors::getFieldErrors)
            .orElseGet(Collections::emptyList)
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());
    }
}
