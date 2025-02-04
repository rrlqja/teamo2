package song.teamo2.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo2.domain.user.dto.SignupDto;
import song.teamo2.domain.user.entity.User;
import song.teamo2.domain.user.repository.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepository;

    @Transactional
    public Long signup(SignupDto signupDto) {
        User user = signupDto.toEntity(passwordEncoder);

        return userRepository.save(user).getId();
    }
}
