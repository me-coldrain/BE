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

    //팀 멤버 조회
    @GetMapping("/api/home/teams/{team_id}/players")
    public ResponseEntity<?> teamMemberGet(@PathVariable("team_id") Long teamId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamMemberService.teamMemberGet(teamId, userDetails.getUser().getId());
    }

    //신청한 팀원 목록
    @GetMapping("/api/teams/{team_id}/offer")
    public ResponseEntity<?> teamMemberOfferGet(@PathVariable("team_id") Long teamId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamMemberService.teamMemberOfferGet(teamId, userDetails);
    }
}
