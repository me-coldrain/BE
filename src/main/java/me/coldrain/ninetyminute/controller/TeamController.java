package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import me.coldrain.ninetyminute.dto.request.TeamParticipateRequest;
import me.coldrain.ninetyminute.dto.request.TeamRegisterRequest;
import me.coldrain.ninetyminute.dto.response.TeamParticipationQuestionResponse;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.service.ParticipationService;
import me.coldrain.ninetyminute.service.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;
    private final ParticipationService participationService;

    /**
     * 팀 등록 API
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/home/teams", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void registerTeam(final TeamRegisterRequest request) {
        log.info("registerTeam.TeamRegisterRequest = {}", request);
        // TODO: 2022-07-06 팀은 회원당 단 1개만 생성할 수 있도록 Validation 처리 필요.
        // TODO: 2022-07-06 팀 사진은 S3에 저장될 수 있도록 구현 필요.
        // TODO: 2022-07-06 회원과 팀을 설정해 줘야 함. -> member.setTeam(team)
        teamService.registerTeam(request);
    }

    /**
     * 팀 목록 조회 API
     */
    @GetMapping("/home/teams")
    public Page<TeamListSearch> selectTeams(final TeamListSearchCondition searchCondition, final Pageable pageable) {
        return teamService.searchTeamList(searchCondition, pageable);
    }

    /**
     * 팀 참여 질문 조회 API
     */
    @GetMapping("/teams/{team_id}/questions")
    public TeamParticipationQuestionResponse getQuestion(final @PathVariable("team_id") Long teamId) {
        final String question = teamService.findQuestionByTeamId(teamId);
        return new TeamParticipationQuestionResponse(question);
    }

    /**
     * 팀 참여 신청 API
     */
    @PostMapping("/home/teams/{team_id}/answer")
    public void participate(
            final @PathVariable("team_id") Long teamId,
            final @RequestBody TeamParticipateRequest request) {

        // TODO: 2022-07-07 로그인 한 회원이 참여 하도록 로직 변경 필요
        participationService.participate(teamId, request);
    }
}
