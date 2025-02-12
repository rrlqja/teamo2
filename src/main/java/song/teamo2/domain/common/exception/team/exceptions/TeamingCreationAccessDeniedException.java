package song.teamo2.domain.common.exception.team.exceptions;

import song.teamo2.domain.common.exception.team.TeamException;

public class TeamingCreationAccessDeniedException extends TeamException {
    public TeamingCreationAccessDeniedException() {
        super("Teaming Creation Access Denied Exception");
    }

    public TeamingCreationAccessDeniedException(String message) {
        super(message);
    }
}
