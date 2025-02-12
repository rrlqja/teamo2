package song.teamo2.domain.common.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public class PostEntity {
    private String title;
    private String content;
    private Long views;

    protected PostEntity(String title, String content) {
        this.title = title;
        this.content = content;
        this.views = 0L;
    }

    protected PostEntity() {

    }
}
