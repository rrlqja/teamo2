package song.teamo2.domain.common.exception.teamMember.exceptions;

import song.teamo2.domain.common.exception.teamMember.TeamMemberException;

public class TeamMemberAccessDeniedException extends TeamMemberException {
    public TeamMemberAccessDeniedException() {
        super("TeamMember Access Denied Exception");
    }

    public TeamMemberAccessDeniedException(String message) {
        super(message);
    }
}
