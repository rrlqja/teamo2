package song.teamo2.domain.common.exception.team.exceptions;

import song.teamo2.domain.common.exception.team.TeamException;

public class TeamModificationAccessDeniedException extends TeamException {
    public TeamModificationAccessDeniedException() {
        super("Team Modification Access Denied Exception");
    }

    public TeamModificationAccessDeniedException(String message) {
        super(message);
    }
}
