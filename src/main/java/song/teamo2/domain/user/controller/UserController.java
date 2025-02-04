package song.teamo2.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo2.domain.user.dto.SignupDto;
import song.teamo2.domain.user.service.UserService;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute("signup") SignupDto signupDto) {

        return "user/signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("signup") SignupDto signupDto) {
        userService.signup(signupDto);

        return "redirect:/login";
    }
}
