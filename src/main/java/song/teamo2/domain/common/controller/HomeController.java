package song.teamo2.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo2.domain.teaming.dto.TeamingPageDto;
import song.teamo2.domain.teaming.service.TeamingService;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final TeamingService teamingService;

    @GetMapping
    public String getHome(@PageableDefault(size = 10, page = 0) Pageable pageable,
                          Model model) {
        Page<TeamingPageDto> teamingPage = teamingService.getTeamingList(pageable);
        model.addAttribute("teamingPage", teamingPage);

        return "home";
    }
}
