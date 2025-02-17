package song.teamo2.domain.common.exception.application.exceptions;

import song.teamo2.domain.common.exception.application.ApplicationException;

public class ApplicationNotFoundException extends ApplicationException {
    public ApplicationNotFoundException() {
        super("Application Not Found Exception");
    }

    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
