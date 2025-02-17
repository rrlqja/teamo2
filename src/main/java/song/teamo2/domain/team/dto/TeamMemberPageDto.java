package song.teamo2.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import song.teamo2.domain.team.entity.TeamMember;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberPageDto {
    private Long id;
    private Long userId;
    private String username;
    private Long teamId;

    public TeamMemberPageDto(TeamMember teamMember) {
        this.id = teamMember.getId();
        this.userId = teamMember.getUser().getId();
        this.username = teamMember.getUser().getUsername();
        this.teamId = teamMember.getTeam().getId();
    }
}
