package song.teamo2.domain.common.exception.teaming.exceptions;

import song.teamo2.domain.common.exception.teaming.TeamingException;

public class TeamingNotFoundException extends TeamingException {
    public TeamingNotFoundException() {
        super("Teaming Not Found Exception");
    }

    public TeamingNotFoundException(String message) {
        super(message);
    }
}
