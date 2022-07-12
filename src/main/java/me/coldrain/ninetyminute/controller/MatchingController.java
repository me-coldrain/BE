package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.request.ApprovedMatchRequest;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import me.coldrain.ninetyminute.service.MatchingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MatchingController {

    private final MatchingService matchingService;

    /*
     * Author: 병민
     * 대결 수락 정보 저장 API
     * apply 의 approved 가 ture 일 때 목록을 조회 할 수 있습니다.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/teams/{apply_team_id}/matches")
    public String searchApprovedMatch(
            final @PathVariable Long apply_team_id,
            final @RequestBody ApprovedMatchRequest approvedMatchRequest,
            final @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return matchingService.approvedMatch(apply_team_id, approvedMatchRequest, userDetails.getUser());
    }
}
