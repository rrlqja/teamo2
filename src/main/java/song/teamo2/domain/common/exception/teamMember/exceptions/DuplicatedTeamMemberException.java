package song.teamo2.domain.common.exception.teamMember.exceptions;

import song.teamo2.domain.common.exception.teamMember.TeamMemberException;

public class DuplicatedTeamMemberException extends TeamMemberException {
    public DuplicatedTeamMemberException() {
        super("Duplicated TeamMember Exception");
    }

    public DuplicatedTeamMemberException(String message) {
        super(message);
    }
}
