package song.teamo2.domain.teaming.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import song.teamo2.domain.teaming.entity.Teaming;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamingDto {
    private Long id;
    private String title;
    private String content;
    private String status;
    private Long teamId;
    private String teamName;
    private String teamInfo;
    private boolean isWriter;

    public TeamingDto(Teaming teaming, boolean isWriter) {
        this.id = teaming.getId();
        this.title = teaming.getTitle();
        this.content = teaming.getContent();
        this.status = teaming.getStatus().toString();
        this.teamId = teaming.getTeam().getId();
        this.teamName = teaming.getTeam().getTeamName();
        this.teamInfo = teaming.getTeam().getTeamInfo();
        this.isWriter = isWriter;
    }
}
