package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.request.TeamRegisterRequest;
import me.coldrain.ninetyminute.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/home/teams", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void registerTeam(final TeamRegisterRequest request) {
        log.info("registerTeam.TeamRegisterRequest = {}", request);
        // TODO: 2022-07-06 팀은 회원당 단 1개만 생성할 수 있도록 Validation 처리 필요.
        // TODO: 2022-07-06 팀 사진은 S3에 저장될 수 있도록 구현 필요.
        teamService.registerTeam(request);
    }
}
