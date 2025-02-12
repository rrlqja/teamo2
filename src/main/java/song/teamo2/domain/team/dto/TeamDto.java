package song.teamo2.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import song.teamo2.domain.team.entity.Team;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private Long id;
    private String teamName;
    private String teamInfo;
    private boolean isAdmin = false;
    private boolean isMember = false;

    public TeamDto(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.teamInfo = team.getTeamInfo();
        this.isAdmin = false;
        this.isMember = false;
    }

    public TeamDto(Team team, boolean isAdmin, boolean isMember) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.teamInfo = team.getTeamInfo();
        this.isAdmin = isAdmin;
        this.isMember = isMember;
    }
}
