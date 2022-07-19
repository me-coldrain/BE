package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import me.coldrain.ninetyminute.service.MemberInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberInfoController {
    private final MemberInfoService memberInfoService;

    //회원정보 조회
    @GetMapping("/api/home/members/{member_id}")
    public ResponseEntity<?> memberInfoGet(@PathVariable("member_id") Long memberId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberInfoService.memberInfoGet(memberId, userDetails);
    }

    //참여 신청중인 팀 신청취소
    @DeleteMapping("/api/home/members/{member_id}/teams/{team_id}/offer")
    public ResponseEntity<?> offerCancelTeam(@PathVariable("member_id") Long memberId, @PathVariable("team_id") Long teamId) {
        return memberInfoService.offerCancelTeam(memberId, teamId);
    }

   //참여한 경기 히스토리 조회
    @GetMapping("/api/home/members/{member_id}/history")
    public ResponseEntity<?> memberGameHistory(@PathVariable("member_id") Long memberId) {
        return memberInfoService.memberGameHistory(memberId);
    }
}
