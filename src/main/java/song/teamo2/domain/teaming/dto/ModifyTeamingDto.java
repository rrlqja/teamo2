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
public class ModifyTeamingDto {
    private String title;
    private String content;

    public ModifyTeamingDto(Teaming teaming) {
        this.title = teaming.getTitle();
        this.content = teaming.getContent();
    }
}
