package me.coldrain.ninetyminute.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamListSearch {

    private Long teamId;

    private String teamName;

    private Long headCount;

    private String mainArea;

    @QueryProjection
    public TeamListSearch(Long teamId, String teamName, Long headCount, String mainArea) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.headCount = headCount;
        this.mainArea = mainArea;
    }
}
