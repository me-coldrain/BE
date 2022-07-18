package me.coldrain.ninetyminute.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.coldrain.ninetyminute.entity.History;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class HistoryResponse {
    private Long historyId;
    private Date matchDate;
    private TeamResponse team;
    private TeamResponse opposingTeam;

    @Builder
    public HistoryResponse(Long historyId, Date matchDate, TeamResponse team, TeamResponse opposingTeam) {
        this.historyId = historyId;
        this.matchDate = matchDate;
        this.team = team;
        this.opposingTeam = opposingTeam;
    }
    @Data
    @AllArgsConstructor
    public static class TeamResponse {
        private String name;
        private String record;
        private Integer score;
    }
}