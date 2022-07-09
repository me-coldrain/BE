package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import me.coldrain.ninetyminute.dto.request.RecruitStartRequest;
import me.coldrain.ninetyminute.dto.request.TeamParticipateRequest;
import me.coldrain.ninetyminute.dto.request.TeamRegisterRequest;
import me.coldrain.ninetyminute.dto.response.TeamParticipationQuestionResponse;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.entity.Team;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import me.coldrain.ninetyminute.service.ParticipationService;
import me.coldrain.ninetyminute.service.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;
    private final ParticipationService participationService;

    /**
     * Author: 상운
     * 팀 등록 API
     * 한 명당 하나의 팀만 개설할 수 있습니다.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/home/teams", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void registerTeam(
            final TeamRegisterRequest request,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {

        log.info("registerTeam.TeamRegisterRequest = {}", request);

        teamService.registerTeam(request, userDetails.getUser().getId());
    }

    /**
     * Author: 상운
     * 팀 목록 조회 API
     */
    @GetMapping("/home/teams")
    public Page<TeamListSearch> selectTeams(
            final TeamListSearchCondition searchCondition,
            final Pageable pageable) {

        return teamService.searchTeamList(searchCondition, pageable);
    }

    /**
     * Author: 상운
     * 팀 참여 질문 조회 API
     */
    @GetMapping("/teams/{team_id}/questions")
    public TeamParticipationQuestionResponse getQuestion(
            final @PathVariable("team_id") Long teamId) {

        final String question = teamService.findQuestionByTeamId(teamId);
        return new TeamParticipationQuestionResponse(question);
    }

    /**
     * Author: 상운
     * 팀 참여 신청 API
     * 한 번 참여 신청한 팀은 다시 참여 신청을 할 수 없습니다.
     */
    @PostMapping("/home/teams/{team_id}/participate")
    public void participate(
            final @PathVariable("team_id") Long teamId,
            final @RequestBody TeamParticipateRequest request,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {

        participationService.participate(teamId, userDetails.getUser(), request);
    }

    /**
     * Author: 상운
     * 신청한 팀원 승인 API
     * 팀 개설자만 승인을 할 수 있습니다.
     */
    @PatchMapping("/teams/{team_id}/members/{member_id}/offer")
    public void approve(
            final @PathVariable("team_id") Long teamId,
            final @PathVariable("member_id") Long memberId,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {

        participationService.approve(teamId, memberId, userDetails.getUser());
    }

    /**
     * Author: 상운
     * 신청한 팀원 거절 API
     * 팀 개설자만 거절을 할 수 있습니다.
     */
    @DeleteMapping("/teams/{team_id}/members/{member_id}/offer")
    public void disapprove(
            final @PathVariable("team_id") Long teamId,
            final @PathVariable("member_id") Long memberId,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {

        participationService.disapprove(teamId, memberId, userDetails.getUser());
    }

    /**
     * Author: 상운
     * 팀원 모집 시작 API
     */
    @PostMapping("/home/teams/{team_id}/recruit/start")
    public void startRecruit(
            final @PathVariable("team_id") Long teamId,
            final @RequestBody RecruitStartRequest request,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {

        teamService.startRecruit(teamId, userDetails.getUser(), request);
    }

    /**
     * Author: 상운
     * 팀원 모집 종료 API
     */
    @PostMapping("/home/teams/{team_id}/recruit/end")
    public void endRecruit(
            final @PathVariable("team_id") Long teamId,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {

        teamService.endRecruit(teamId, userDetails.getUser());
    }

    /**
     * Author: 상운
     * 대결 등록 API
     */
    @PostMapping("/home/teams/{team_id}/match/regist")
    public void registMatch(
            final @PathVariable("team_id") Long teamId,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {

        teamService.registMatch(teamId, userDetails.getUser());
    }
}