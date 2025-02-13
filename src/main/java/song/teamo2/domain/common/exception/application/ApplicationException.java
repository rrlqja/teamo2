package song.teamo2.domain.common.exception.application;

import song.teamo2.domain.common.exception.TeamoException;

public class ApplicationException extends TeamoException {
    public ApplicationException() {
        super("Application Exception");
    }

    public ApplicationException(String message) {
        super(message);
    }
}
