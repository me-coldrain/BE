package me.coldrain.ninetyminute.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MyTeamRankResponse {
    private teamCaptain teamCaptain;
    private List<teamMember> teamMember;

    @NoArgsConstructor
    @Setter
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class teamCaptain{
        private Long teamId;
        private String mainArea;
        private String teamName;
        private int winPoint;
        private int myOpenTeamRank;
    }

    @NoArgsConstructor
    @Setter
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class teamMember{
        private Long teamId;
        private String mainArea;
        private String teamName;
        private int winPoint;
        private int myTeamRank;
    }
}
