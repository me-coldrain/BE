package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import me.coldrain.ninetyminute.service.RankService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RankController {
    private final RankService rankService;

    //팀 랭킹 조회
    @GetMapping("/api/home/rank/teams")
    public ResponseEntity<?> teamRankGet() {
        return rankService.teamRankGet();
    }

    //개인 포지션 랭킹 조회
    @GetMapping("/api/home/rank/members")
    public ResponseEntity<?> memberRankGet(@RequestParam("ability") String ability) {
        return rankService.memberRankGet(ability);
    }

    //로그인 사용자 개인 랭킹
    @GetMapping("/api/home/rank/members/myranking")
    public ResponseEntity<?> myRankGet(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return rankService.myRankGet(userDetails);
    }

    //로그인 사용자가 참여하고 있는 팀의 랭킹 조회
    @GetMapping("/api/home/rank/members/myranking/teams")
    public ResponseEntity<?> myTeamRankGet(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return rankService.myTeamRankGet(userDetails);
    }
}
