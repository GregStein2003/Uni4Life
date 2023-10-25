package br.edu.unisinos.uni4life.exception;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.INTERNAL_ERROR;

import br.edu.unisinos.uni4life.domain.enumeration.ErrorType;

public class ServerErrorException extends AbstractErrorException {

    private static final long serialVersionUID = -5809529426540256544L;

    private ErrorType errorType;

    public ServerErrorException(final String msg) {
        super(msg);
        this.errorType = INTERNAL_ERROR;
    }

    public ServerErrorException(final ErrorType errorType, final String msg) {
        super(msg);
        this.errorType = errorType;
    }

    public ServerErrorException(final String msg, final Throwable th) {
        super(msg, th);
        this.errorType = INTERNAL_ERROR;
    }

    public ServerErrorException(final ErrorType errorType, final String msg, final Throwable th) {
        super(msg, th);
        this.errorType = errorType;
    }

    @Override
    public ErrorType getErrorType() {
        return this.errorType;
    }

}
