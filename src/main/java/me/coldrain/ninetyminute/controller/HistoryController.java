package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.response.HistoryDetailResponse;
import me.coldrain.ninetyminute.dto.response.HistoryResponse;
import me.coldrain.ninetyminute.entity.History;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import me.coldrain.ninetyminute.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HistoryController {

    private final HistoryService historyService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("teams/{team_id}/history")
    public List<HistoryResponse> searchMatchHistory(
            @PathVariable("team_id") Long teamId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return historyService.searchMatchHistory(teamId, userDetails.getUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/teams/{team_id}/history/{history_id}")
    public HistoryDetailResponse searchMatchHistoryDetail(
            @PathVariable("team_id") Long teamId,
            @PathVariable("history_id") Long historyId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return historyService.searchMatchHistoryDetail;
    }
}
