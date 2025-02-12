package song.teamo2.domain.common.exception.teaming;

import song.teamo2.domain.common.exception.TeamoException;

public class TeamingException extends TeamoException {
    public TeamingException() {
        super("Teaming Exception");
    }

    public TeamingException(String message) {
        super(message);
    }
}
