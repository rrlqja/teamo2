package song.teamo2.domain.team.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo2.domain.common.entity.DateEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends DateEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;
    private String teamInfo;

    public static Team of(String teamName, String teamInfo) {
        return new Team(teamName, teamInfo);
    }

    private Team(String teamName, String teamInfo) {
        this.teamName = teamName;
        this.teamInfo = teamInfo;
    }

    public void modify(String teamName, String teamInfo) {
        this.teamName = teamName;
        this.teamInfo = teamInfo;
    }
}
