package song.teamo2.domain.common.exception.teaming.exceptions;

import song.teamo2.domain.common.exception.teaming.TeamingException;

public class TeamingModificationAccessDeniedException extends TeamingException {
    public TeamingModificationAccessDeniedException() {
        super("Teaming Modification Access Denied Exception");
    }

    public TeamingModificationAccessDeniedException(String message) {
        super(message);
    }
}
