package song.teamo2.domain.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo2.domain.application.service.ApplicationService;
import song.teamo2.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/approve/{applicationId}")
    public String postApprove(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PathVariable("applicationId") Long applicationId,
                              RedirectAttributes redirectAttributes) {
        Long teamId = applicationService.approveApplication(userDetails.getUser(), applicationId);

        redirectAttributes.addAttribute("teamId", teamId);

        return "redirect:/team/{teamId}";
    }
}
