package song.teamo2.domain.common.exception.teamMember.exceptions;

import song.teamo2.domain.common.exception.teamMember.TeamMemberException;

public class AlreadyExistTeamMemberException extends TeamMemberException {
    public AlreadyExistTeamMemberException() {
        super("Already Exist TeamMember Exception");
    }

    public AlreadyExistTeamMemberException(String message) {
        super(message);
    }
}
