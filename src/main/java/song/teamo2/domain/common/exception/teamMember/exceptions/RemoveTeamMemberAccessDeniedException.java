package song.teamo2.domain.common.exception.teamMember.exceptions;

import song.teamo2.domain.common.exception.teamMember.TeamMemberException;

public class RemoveTeamMemberAccessDeniedException extends TeamMemberException {
    public RemoveTeamMemberAccessDeniedException() {
        super("Remove TeamMember Access Denied Exception");
    }

    public RemoveTeamMemberAccessDeniedException(String message) {
        super(message);
    }
}
