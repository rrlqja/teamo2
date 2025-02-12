package song.teamo2.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamingDto {
    private String title;
    private String content;
}
