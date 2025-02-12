package song.teamo2.domain.common.exception.teamMember;

import song.teamo2.domain.common.exception.TeamoException;

public class TeamMemberException extends TeamoException {
    public TeamMemberException() {
        super("TeamMember Exception");
    }

    public TeamMemberException(String message) {
        super(message);
    }
}
