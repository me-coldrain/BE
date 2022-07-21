package me.coldrain.ninetyminute.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MemberGameHistoryResponse {
    private boolean myHistory;
    private Long historyId;
    private Date matchDate;
    private Team team;
    private OpposingTeam opposingTeam;

    @NoArgsConstructor
    @Setter
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Team {
        private String name;
        private String record;
        private int score;
    }

    @NoArgsConstructor
    @Setter
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class OpposingTeam {
        private String name;
        private String record;
        private int score;
    }
}
