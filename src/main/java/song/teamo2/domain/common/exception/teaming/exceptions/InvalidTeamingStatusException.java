package song.teamo2.domain.common.exception.teaming.exceptions;

import song.teamo2.domain.common.exception.teaming.TeamingException;

public class InvalidTeamingStatusException extends TeamingException {
    public InvalidTeamingStatusException() {
        super("Invalid Teaming Status Exception");
    }

    public InvalidTeamingStatusException(String message) {
        super(message);
    }
}
