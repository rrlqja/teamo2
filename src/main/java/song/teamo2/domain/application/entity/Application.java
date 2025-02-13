package song.teamo2.domain.application.entity;

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
public class Application extends PostEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public static Application create(User user, Team team, String title, String content) {
        return new Application(user, team, title, content);
    }

    private Application(User user, Team team, String title, String content) {
        super(title, content);
        this.user = user;
        this.team = team;
        this.status = ApplicationStatus.PENDING;
    }
}
