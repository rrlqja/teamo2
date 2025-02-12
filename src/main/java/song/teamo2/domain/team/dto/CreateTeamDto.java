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
public class CreateTeamDto {
    private String teamName;
    private String teamInfo;

    public Team toEntity() {
        return Team.of(teamName, teamInfo);
    }
}
