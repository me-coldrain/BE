package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import me.coldrain.ninetyminute.service.TeamMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    @GetMapping("/api/home/teams/{team_id}/players")
    public ResponseEntity<?> teamMemberGet(@PathVariable Long team_id,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamMemberService.teamMemberGet(team_id, userDetails.getUser().getId());
    }
}
