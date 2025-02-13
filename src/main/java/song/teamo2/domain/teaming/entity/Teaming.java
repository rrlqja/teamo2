package song.teamo2.domain.teaming.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo2.domain.common.entity.PostEntity;
import song.teamo2.domain.team.entity.Team;
import song.teamo2.domain.user.entity.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teaming extends PostEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @Enumerated(EnumType.STRING)
    private TeamingStatus status;

    public static Teaming create(User writer, Team team, String title, String content) {
        return new Teaming(writer, team, title, content);
    }

    private Teaming(User writer, Team team, String title, String content) {
        super(title, content);
        this.team = team;
        this.writer = writer;
        this.status = TeamingStatus.RECRUITING;
    }

    public void modify(String title, String content) {
        setTitle(title);
        setContent(content);
    }

    public void toggleStatus() {
        if (this.status == TeamingStatus.RECRUITING) {
            this.status = TeamingStatus.CLOSED;
        } else {
            this.status = TeamingStatus.RECRUITING;
        }
    }
}
