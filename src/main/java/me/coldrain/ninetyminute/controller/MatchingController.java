package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.request.ApprovedMatchRequest;
import me.coldrain.ninetyminute.dto.request.MatchScoreRequest;
import me.coldrain.ninetyminute.dto.request.fieldMemberRequest;
import me.coldrain.ninetyminute.dto.response.OfferMatchResponse;
import me.coldrain.ninetyminute.dto.response.ApprovedMatchResponse;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import me.coldrain.ninetyminute.service.MatchingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MatchingController {

    private final MatchingService matchingService;

    /*
     * Author: 병민
     * 대결 수락 목록 조회 API
     * 대결이 신청된 목록을 대결 신청 받은 팀의 팀장이 조회하는 API.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/teams/{team_id}/matches/offer")
    public List<OfferMatchResponse> searchOfferMatches(
            final @PathVariable("team_id") Long teamId,
            final @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return matchingService.searchOfferMatches(teamId, userDetails.getUser());
    }

    /*
     * Author: 병민
     * 대결 수락 정보 저장 API
     * 대결 수락 시 대결 상세 정보 저장 및 apply 상태 변경.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/teams/{apply_team_id}/apply/{apply_id}")
    public String approveApplyMatch(
            final @PathVariable("apply_team_id") Long applyTeamId,
            final @PathVariable("apply_id") Long applyId,
            final @RequestBody ApprovedMatchRequest approvedMatchRequest,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return matchingService.approveApplyMatch(applyTeamId, applyId, approvedMatchRequest, userDetails.getUser());
    }

    /*
     * Author: 병민
     * 대결 신청 거절 API
     * 대결 신청 팀의 id을 전달 받아야 함.
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/teams/{apply_team_id}/apply/{apply_id}")
    public void cancelApplyMatch(
            final @PathVariable("apply_team_id") Long applyTeamId,
            final @PathVariable("apply_id") Long applyId,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {
        matchingService.rejectApplyMatch(applyTeamId, applyId, userDetails.getUser());
    }

    /*
     * 대결 성사 목록 조회 API
     * apply 의 approved 가 ture 일 때 목록을 조회 할 수 있습니다.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/teams/{team_id}/matches")
    public List<ApprovedMatchResponse> searchApprovedMatch(
            final @PathVariable("team_id") Long teamId,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return matchingService.searchApprovedMatch(teamId, userDetails.getUser());
    }

    /*
     * 대결 성사 목록 상세 조회 API
     * 성사 된 대결의 상세 페이지 정보
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/teams/{team_id}/matches/{match_id}/detail")
    public ApprovedMatchResponse searchApprovedMatchDetail(
            @PathVariable("team_id") Long teamId,
            @PathVariable("match_id") Long matchId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return matchingService.searchApprovedMatchDetail(teamId, matchId, userDetails.getUser());
    }

    /*
     * Author: 병민
     * 성사 대결 팀 포매이션 등록 API
     * 비회원의 경우 memberId를 null 처리
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/teams/{team_id}/matches/{match_id}/formation")
    public void makeTeamFormation(
            @PathVariable("team_id") Long teamId,
            @PathVariable("match_id") Long matchId,
            @RequestBody List<fieldMemberRequest> fieldMemberRequestList,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        matchingService.makeTeamFormation(teamId, matchId, fieldMemberRequestList, userDetails.getUser());
    }

    /*
     * Author: 병민
     * 성사 대결 전 취소 API
     * 우천 및 기타 상황 발생 시 대결 취소
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/teams/{team_id}/matches/{match_id}")
    public void cancelApprovedMatch(
            @PathVariable("team_id") Long teamId,
            @PathVariable("match_id") Long matchId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        matchingService.cancelApprovedMatch(teamId, matchId, userDetails.getUser());
    }

    /*
     * Author: 병민
     * 대결 종료 확인 API
     * 대결 종료 확인 버튼 수행 API -> 대결 각 팀의 경기 종료 상태를 apply 에 저장.
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/teams/{team_id}/matches/{match_id}")
    public void confirmEndMatch(
            final @PathVariable("team_id") Long teamId,
            final @PathVariable("match_id") Long matchId,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {
        matchingService.confirmEndMatch(teamId, matchId, userDetails.getUser());
    }

    /*
     * Author: 병민
     * 대결 결과 점수 입력 API
     * 대결 후 경기 결과 입력
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/teams/{team_id}/matches/{match_id}/score")
    public void writeMatchScore(
            final @PathVariable("team_id") Long teamId,
            final @PathVariable("match_id") Long matchId,
            final @RequestBody MatchScoreRequest matchScoreRequest,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {
        matchingService.writeMatchScore(teamId, matchId, matchScoreRequest, userDetails.getUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/teams/{team_id}/matches/{match_id}/score")
    public void correctMatchScore(
            final @PathVariable("team_id") Long teamId,
            final @PathVariable("match_id") Long matchId,
            final @RequestBody MatchScoreRequest matchScoreRequest,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {
        matchingService.correctMatchScore(teamId, matchId, matchScoreRequest, userDetails.getUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/teams/{team_id}/matches/{match_id}/score/admit")
    public void confirmMatchScore(
            final @PathVariable("team_id") Long teamId,
            final @PathVariable("match_id") Long matchId,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {
        matchingService.confirmMatchScore(teamId, matchId, userDetails.getUser());
    }
}