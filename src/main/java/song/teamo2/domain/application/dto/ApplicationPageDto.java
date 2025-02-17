package song.teamo2.domain.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import song.teamo2.domain.application.entity.Application;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPageDto {
    private Long id;
    private String title;
    private String status;

    public ApplicationPageDto(Application application) {
        this.id = application.getId();
        this.title = application.getTitle();
        this.status = application.getStatus().name();
    }
}
