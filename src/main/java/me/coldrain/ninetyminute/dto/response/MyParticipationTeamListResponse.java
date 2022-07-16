package me.coldrain.ninetyminute.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.coldrain.ninetyminute.entity.Time;
import me.coldrain.ninetyminute.entity.Weekday;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class MyParticipationTeamListResponse {
    private boolean captain;
    private Long teamId;
    private String teamName;
    private int headCount;
    private String mainArea;
    private String preferredArea;
    private List<String> weekdays;
    private List<String> time;
    private int winPoint;
    private double winRate;
    private boolean recruit;
    private boolean match;
    private int totalGameCount;
    private int winCount;
    private int drawCount;
    private int loseCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
