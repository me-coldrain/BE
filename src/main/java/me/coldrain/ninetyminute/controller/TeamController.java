package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import me.coldrain.ninetyminute.dto.request.TeamRegisterRequest;
import me.coldrain.ninetyminute.entity.Team;
import me.coldrain.ninetyminute.service.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

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
}
