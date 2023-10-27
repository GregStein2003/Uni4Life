package br.edu.unisinos.uni4life.interceptor;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.edu.unisinos.uni4life.dto.response.ErrorResponse;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.exception.ServerErrorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerErrorInterceptor {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final ClientErrorException clientErrorException) {
        final ErrorResponse error = ErrorResponse.build(clientErrorException);
        log.warn("Erro com a requisição: ", clientErrorException);
        return new ResponseEntity<>(error, error.getErrorType().getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final MethodArgumentNotValidException exception) {
        final ErrorResponse error = ErrorResponse.build(exception);
        log.warn("Erro com a requisição: ", exception);
        return new ResponseEntity<>(error, error.getErrorType().getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final MissingServletRequestParameterException exception) {
        final ErrorResponse error = ErrorResponse.build(exception);
        log.warn("Erro com a requisição: ", exception);
        return new ResponseEntity<>(error, error.getErrorType().getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final MethodArgumentTypeMismatchException exception) {
        final ErrorResponse error = ErrorResponse.build(exception);
        log.warn("Erro com a requisição: ", exception);
        return new ResponseEntity<>(error, error.getErrorType().getHttpStatus());
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final HttpMessageNotReadableException exception) {
        final ErrorResponse error = ErrorResponse.build(exception);
        log.warn("Não foi possível converter a requisição", exception);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final ServerErrorException serverErrorException) {
        final ErrorResponse error = ErrorResponse.build(serverErrorException);
        log.warn("Erro Interno: {}", serverErrorException.getMessage());
        return new ResponseEntity<>(error, serverErrorException.getErrorType().getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        final ErrorResponse error = ErrorResponse.buildDefault();
        log.warn("Erro Interno: {}", exception.getMessage());
        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }
}
