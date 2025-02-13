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
public class TeamingPageDto {
    private Long teamingId;
    private String title;
    private String writerName;

    public TeamingPageDto(Teaming teaming) {
        this.teamingId = teaming.getId();
        this.title = teaming.getTitle();
        this.writerName = teaming.getWriter().getUsername();
    }
}
