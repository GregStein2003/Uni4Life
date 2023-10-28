package br.edu.unisinos.uni4life.domain.enumeration;


import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {

    BUSINESS(HttpStatus.BAD_REQUEST),

    FORBIDDEN(HttpStatus.FORBIDDEN),

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),

    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED),
    NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED),
    NOT_FOUND(HttpStatus.NOT_FOUND),

    VALIDATION(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

}
