package song.teamo2.domain.team.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo2.domain.application.dto.ApplicationPageDto;
import song.teamo2.domain.team.dto.CreateTeamDto;
import song.teamo2.domain.team.dto.CreateTeamingDto;
import song.teamo2.domain.team.dto.ModifyTeamDto;
import song.teamo2.domain.team.dto.TeamDto;
import song.teamo2.domain.team.dto.TeamMemberPageDto;
import song.teamo2.domain.team.entity.Team;
import song.teamo2.domain.team.service.TeamService;
import song.teamo2.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/createTeam")
    public String getCreateTeam(@ModelAttribute("team") CreateTeamDto createTeamDto) {

        return "team/createTeam";
    }

    @PostMapping("/createTeam")
    public String postCreateTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 CreateTeamDto createTeamDto,
                                 RedirectAttributes redirectAttributes) {
        Long teamId = teamService.createTeam(userDetails.getUser(), createTeamDto);

        redirectAttributes.addAttribute("teamId", teamId);

        return "redirect:/team/{teamId}";
    }

    @GetMapping("/teamList")
    public String getTeamPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PageableDefault(size = 10, page = 0) Pageable pageable,
                              Model model) {
        Page<Team> userTeamPage = teamService.getUserTeamPage(userDetails.getUser(), pageable);

        model.addAttribute("teamPage", userTeamPage);

        return "team/teamList";
    }

    @GetMapping("/{teamId}")
    public String getTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          @PathVariable("teamId") Long teamId,
                          Model model) {
        TeamDto teamDto = teamService.getTeam(userDetails == null ? null : userDetails.getUser(), teamId);

        model.addAttribute("team", teamDto);

        return "team/team";
    }

    @PostMapping("/{teamId}/exitTeam")
    public String postExitTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @PathVariable("teamId") Long teamId) {
        teamService.exitTeam(userDetails.getUser(), teamId);

        return "redirect:/team/teamList";
    }

    @GetMapping("/modify/{teamId}")
    public String getEdit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          @PathVariable("teamId") Long teamId,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        ModifyTeamDto modifyTeam = teamService.getTeamForModify(userDetails.getUser(), teamId);

        if (modifyTeam != null) {
            model.addAttribute("team", modifyTeam);
            return "team/modifyTeam";
        }

        redirectAttributes.addAttribute("teamId", teamId);

        return "redirect:/team/{teamId}";
    }

    @PostMapping("modify/{teamId}")
    public String postEditTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @PathVariable("teamId") Long teamId,
                               @ModelAttribute("team") ModifyTeamDto modifyTeamDto,
                               RedirectAttributes redirectAttributes) {
        Long modifiedTeamId = teamService.modifyTeam(userDetails.getUser(), teamId, modifyTeamDto);

        redirectAttributes.addAttribute("teamId", modifiedTeamId);

        return "redirect:/team/{teamId}";
    }

    @GetMapping("/teaming/{teamId}")
    public String getTeaming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @PathVariable("teamId") Long teamId,
                             @ModelAttribute("teaming") CreateTeamingDto createTeamingDto) {
        teamService.getCreateTeaming(userDetails.getUser(), teamId);

        return "team/createTeaming";
    }

    @PostMapping("/teaming/{teamId}")
    public String postTeaming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PathVariable("teamId") Long teamId,
                              CreateTeamingDto createTeamingDto,
                              RedirectAttributes redirectAttributes) {
        Long teamingId = teamService.createTeaming(userDetails.getUser(), teamId, createTeamingDto);

        redirectAttributes.addAttribute("teamingId", teamingId);

        return "redirect:/teaming/{teamingId}";
    }

    @GetMapping("/applicationList/{teamId}")
    public String getApplicationPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @PathVariable("teamId") Long teamId,
                                     @PageableDefault(size = 10, page = 0) Pageable pageable,
                                     Model model) {
        Page<ApplicationPageDto> applicationPage = teamService.getApplicationPage(userDetails.getUser(), teamId, pageable);

        model.addAttribute("applicationPage", applicationPage);

        return "team/applicationList";
    }

    @GetMapping("/{teamId}/teamMemberList")
    public String getTeamMemberList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable("teamId") Long teamId,
                                    Model model,
                                    @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<TeamMemberPageDto> teamMemberPage = teamService.getTeamMemberPage(userDetails.getUser(), teamId, pageable);

        model.addAttribute("teamMemberPage", teamMemberPage);

        return "team/teamMemberList";
    }

    @PostMapping("/{teamId}/removeTeamMember/{teamMemberUserId}")
    public String PostRemoveTeamMember(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @PathVariable("teamId") Long teamId,
                                       @PathVariable("teamMemberUserId") Long teamMemberUserId,
                                       RedirectAttributes redirectAttributes) {
        Long removeTeamId = teamService.removeTeamMember(userDetails.getUser(), teamId, teamMemberUserId);

        redirectAttributes.addAttribute("teamId", removeTeamId);

        return "redirect:/team/{teamId}";
    }
}
