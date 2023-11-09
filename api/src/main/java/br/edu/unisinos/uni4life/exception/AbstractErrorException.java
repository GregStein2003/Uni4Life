package br.edu.unisinos.uni4life.exception;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import br.edu.unisinos.uni4life.domain.enumeration.ErrorType;
import lombok.Getter;

@Getter
public abstract class AbstractErrorException extends RuntimeException {

    private static final long serialVersionUID = -8004100005407012963L;

    private final String field;
    private final Map<String, String> details;

    public AbstractErrorException(final String msg) {
        super(msg);
        details = new ConcurrentHashMap<>();
        field = null;
    }

    public AbstractErrorException(final String msg, final String field) {
        super(msg);
        details = new ConcurrentHashMap<>();
        this.field = field;
    }

    public AbstractErrorException(final String msg, final String field, final Throwable th) {
        super(msg, th);
        details = new ConcurrentHashMap<>();
        this.field = field;
    }

    public AbstractErrorException(final String msg, final Throwable th) {
        super(msg, th);
        details = new ConcurrentHashMap<>();
        field = null;
    }

    public abstract ErrorType getErrorType();
}
