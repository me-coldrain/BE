package me.coldrain.ninetyminute.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TeamListSearch {

    private Long teamId;

    private String teamName;

    private Long headCount;

    private String mainArea;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @QueryProjection
    public TeamListSearch(Long teamId, String teamName, Long headCount, String mainArea, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.headCount = headCount;
        this.mainArea = mainArea;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
