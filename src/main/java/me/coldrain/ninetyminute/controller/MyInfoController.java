package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.service.MyInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MyInfoController {
    private final MyInfoService myInfoService;

    //회원정보 조회
    @GetMapping("/api/members/{member_id}")
    public ResponseEntity<?> myInfoGet(@PathVariable Long member_id) {
        return myInfoService.myInfoGet(member_id);
    }

    //참여한 팀 조회
    @GetMapping("/api/members/{member_id}/teams")
    public ResponseEntity<?> myTeamGet(@PathVariable Long member_id) {
        return myInfoService.myTeamGet(member_id);
    }

    //참여 신청중인 팀 조회
    @GetMapping("/api/members/{member_id}/teams/offer")
    public ResponseEntity<?> offerTeamGet(@PathVariable Long member_id) {
        return myInfoService.offerTeamGet(member_id);
    }

    //참여 신청중인 팀 신청취소
    @DeleteMapping("/api/members/{member_id}/teams/{team_id}/offer")
    public ResponseEntity<?> offerCancelTeam(@PathVariable Long member_id, @PathVariable Long team_id) {
        return myInfoService.offerCancelTeam(member_id, team_id);
    }

   //참여한 경기 히스토리 조회
    @GetMapping("/api/members/{member_id}/history")
    public ResponseEntity<?> myGameHistory(@PathVariable Long member_id) {
        return myInfoService.myGameHistory(member_id);
    }
}
