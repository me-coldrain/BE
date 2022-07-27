package me.coldrain.ninetyminute.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ParticipationTeamMatchResponse {

    private Long teamId;
    private Boolean isCaptain;
    private String teamName;
    private Integer teamMemberCount;
    private Integer teamPoint;
    private Double teamWinRate;
    private Integer teamTotalGameCount;
    private Integer teamWinCount;
    private Integer teamDrawCount;
    private Integer teamLoseCount;
    private String matchLocation;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Boolean matchStatus;    // true : 대결 예정

    public void changeIsCaptain(Boolean isCaptain) {
        this.isCaptain = isCaptain;
    }

    public void changeMatchLocation(String matchLocation) {
        this.matchLocation = matchLocation;
    }

    public void changeMatchStatus(Boolean matchStatus) {
        this.matchStatus = matchStatus;
    }

    @Builder
    public ParticipationTeamMatchResponse(Long teamId, Boolean isCaptain,
                                          String teamName, Integer teamMemberCount, Integer teamPoint,
                                          Double teamWinRate, Integer teamTotalGameCount, Integer teamWinCount,
                                          Integer teamDrawCount, Integer teamLoseCount, String matchLocation,
                                          LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean matchStatus) {

        this.teamId = teamId;
        this.isCaptain = isCaptain;
        this.teamName = teamName;
        this.teamMemberCount = teamMemberCount;
        this.teamPoint = teamPoint;
        this.teamWinRate = teamWinRate;
        this.teamTotalGameCount = teamTotalGameCount;
        this.teamWinCount = teamWinCount;
        this.teamDrawCount = teamDrawCount;
        this.teamLoseCount = teamLoseCount;
        this.matchLocation = matchLocation;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.matchStatus = matchStatus;
    }
}
