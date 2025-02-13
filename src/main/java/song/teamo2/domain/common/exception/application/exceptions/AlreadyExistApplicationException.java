package song.teamo2.domain.common.exception.application.exceptions;

import song.teamo2.domain.common.exception.application.ApplicationException;

public class AlreadyExistApplicationException extends ApplicationException {
    public AlreadyExistApplicationException() {
        super("Already Exist Application Exception");
    }

    public AlreadyExistApplicationException(String message) {
        super(message);
    }
}
