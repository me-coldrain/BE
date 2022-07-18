package me.coldrain.ninetyminute.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TeamInfoResponse {
    private String teamName;
    private String introduce;
    private String teamImageFileUrl;
    private boolean recruit;
    private boolean match;
    private int winPoint;
    private int totalGameCount;
    private int winCount;
    private int drawCount;
    private int loseCount;
    private double winRate;
    private String mainArea;
    private String preferredArea;
    private List<String> weekdays;
    private List<String> time;
    private int headCount;
    private boolean teamCaptain;
    private boolean otherCaptain;
    private boolean participate;
    private RecentMatchHistory recentMatchHistory;

    @NoArgsConstructor
    @Setter
    public static class RecentMatchHistory {
        private Long historyId;
        private Date matchDate;
        private Team team;
        private OpposingTeam opposingTeam;

        @AllArgsConstructor
        @Setter
        public static class Team {
            private String name;
            private String result;
            private int score;
        }

        @AllArgsConstructor
        @Setter
        public static class OpposingTeam {
            private String name;
            private String result;
            private int score;
        }
    }
}
