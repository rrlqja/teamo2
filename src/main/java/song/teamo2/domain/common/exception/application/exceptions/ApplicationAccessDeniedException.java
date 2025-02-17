package song.teamo2.domain.common.exception.application.exceptions;

import song.teamo2.domain.common.exception.application.ApplicationException;

public class ApplicationAccessDeniedException extends ApplicationException {
    public ApplicationAccessDeniedException() {
        super("Application Access Denied Exception");
    }

    public ApplicationAccessDeniedException(String message) {
        super(message);
    }
}
