package song.teamo2.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;
import song.teamo2.domain.user.entity.User;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    private String username;
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.of(username, passwordEncoder.encode(password));
    }
}
