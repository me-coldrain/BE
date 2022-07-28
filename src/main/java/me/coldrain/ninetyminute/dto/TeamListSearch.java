package me.coldrain.ninetyminute.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.coldrain.ninetyminute.entity.Time;
import me.coldrain.ninetyminute.entity.Weekday;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TeamListSearch {

    private Long teamId;

    private String teamName;

    private Long headCount;

    private String mainArea;

    private String preferredArea;
    private List<String> weekdays;
    private List<String> time;
    private Double winRate;
    private Boolean recruit;
    private Boolean match;
    private Integer totalGameCount;
    private Integer winCount;
    private Integer drawCount;
    private Integer loseCount;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @QueryProjection
    public TeamListSearch(Long teamId, String teamName, Long headCount, String mainArea, String preferredArea, Double winRate, Boolean recruit, Boolean match, Integer totalGameCount, Integer winCount, Integer drawCount, Integer loseCount, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.headCount = headCount;
        this.mainArea = mainArea;
        this.preferredArea = preferredArea;
        this.winRate = winRate;
        this.recruit = recruit;
        this.match = match;
        this.totalGameCount = totalGameCount;
        this.winCount = winCount;
        this.drawCount = drawCount;
        this.loseCount = loseCount;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
