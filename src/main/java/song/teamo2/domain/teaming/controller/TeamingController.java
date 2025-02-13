package song.teamo2.domain.teaming.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo2.domain.teaming.dto.ModifyTeamingDto;
import song.teamo2.domain.teaming.dto.TeamingDto;
import song.teamo2.domain.teaming.dto.TeamingPageDto;
import song.teamo2.domain.teaming.service.TeamingService;
import song.teamo2.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/teaming")
@RequiredArgsConstructor
public class TeamingController {
    private final TeamingService teamingService;

    @GetMapping("/teamingList")
    public String getTeamingList(@PageableDefault Pageable pageable,
                                 Model model) {
        Page<TeamingPageDto> teamingPage = teamingService.getTeamingList(pageable);

        model.addAttribute("teamingPage", teamingPage);

        return "teaming/teamingList";
    }

    @GetMapping("/{teamingId}")
    public String getTeaming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @PathVariable("teamingId") Long teamingId,
                             Model model) {
        TeamingDto teaming = teamingService.getTeaming(userDetails == null ? null : userDetails.getUser(), teamingId);

        model.addAttribute("teaming", teaming);

        return "teaming/teaming";
    }

    @GetMapping("/modify/{teamingId}")
    public String getModifyTeaming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable("teamingId") Long teamingId,
                                   Model model) {
        ModifyTeamingDto teaming = teamingService.getTeamingForModify(userDetails == null ? null : userDetails.getUser(), teamingId);

        model.addAttribute("teaming", teaming);

        return "teaming/modifyTeaming";
    }

    @PostMapping("/modify/{teamingId}")
    public String postModifyTeaming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable("teamingId") Long teamingId,
                                    ModifyTeamingDto modifyTeamingDto,
                                    RedirectAttributes redirectAttributes) {
        Long modifiedTeamingId = teamingService.modifyTeaming(userDetails.getUser(), teamingId, modifyTeamingDto);

        redirectAttributes.addAttribute("teamingId", modifiedTeamingId);

        return "redirect:/teaming/{teamingId}";
    }

    @GetMapping("/toggleStatus/{teamingId}")
    public String closeTeamingStatus(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @PathVariable("teamingId") Long teamingId,
                                     RedirectAttributes redirectAttributes) {
        Long closedTeamingId = teamingService.closeTeaming(userDetails == null ? null : userDetails.getUser(), teamingId);

        redirectAttributes.addAttribute("teamingId", closedTeamingId);

        return "redirect:/teaming/{teamingId}";
    }
}
