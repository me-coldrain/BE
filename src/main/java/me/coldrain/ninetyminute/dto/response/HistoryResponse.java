package me.coldrain.ninetyminute.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HistoryResponse {
    private Long historyId;
    private TeamResponse team;
    private TeamResponse opposingTeam;

    @Builder
    public HistoryResponse(Long historyId, TeamResponse team, TeamResponse opposingTeam) {
        this.historyId = historyId;
        this.team = team;
        this.opposingTeam = opposingTeam;
    }
    @Data
    @AllArgsConstructor
    public static class TeamResponse {
        private String name;
        private String result;
        private Integer score;
    }
}