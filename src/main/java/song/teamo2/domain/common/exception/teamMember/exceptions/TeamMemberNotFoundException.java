package song.teamo2.domain.common.exception.teamMember.exceptions;

import song.teamo2.domain.common.exception.teamMember.TeamMemberException;

public class TeamMemberNotFoundException extends TeamMemberException {
    public TeamMemberNotFoundException() {
        super("TeamMember Not Found Exception");
    }

    public TeamMemberNotFoundException(String message) {
        super(message);
    }
}
