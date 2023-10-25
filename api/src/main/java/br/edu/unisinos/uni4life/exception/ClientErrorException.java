package br.edu.unisinos.uni4life.exception;

import java.util.Map;

import br.edu.unisinos.uni4life.domain.enumeration.ErrorType;

public class ClientErrorException extends AbstractErrorException {

    private static final long serialVersionUID = 1486297407038116871L;

    private final ErrorType errorType;

    public ClientErrorException(final ErrorType errorType, final String msg) {
        super(msg);
        this.errorType = errorType;
    }

    public ClientErrorException(ErrorType errorType, String msg, final Throwable th) {
        super(msg, th);
        this.errorType = errorType;
    }

    public ClientErrorException(ErrorType errorType, String msg, Map details) {
        super(msg);
        this.errorType = errorType;
        this.getDetails().putAll(details);
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }

}
