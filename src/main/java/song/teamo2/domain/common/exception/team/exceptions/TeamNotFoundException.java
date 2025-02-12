package song.teamo2.domain.common.exception.team.exceptions;

import song.teamo2.domain.common.exception.team.TeamException;

public class TeamNotFoundException extends TeamException {
    public TeamNotFoundException() {
        super("Team Not Found Exception");
    }

    public TeamNotFoundException(String message) {
        super(message);
    }
}
