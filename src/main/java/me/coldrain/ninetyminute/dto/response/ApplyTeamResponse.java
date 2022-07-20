package me.coldrain.ninetyminute.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class ApplyTeamResponse {

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
    private String mainArea;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private Boolean applyStatus;    // true : 신청 완료, false : 팀 허가 대기중

    public void changeIsCaptain (Boolean isCaptain) {
        this.isCaptain = isCaptain;
    }
    public void changeApplyStatus (Boolean applyStatus) {
        this.applyStatus = applyStatus;
    }

    @Builder
    public ApplyTeamResponse(Long teamId, Boolean isCaptain,
                         String teamName, Integer teamMemberCount, Integer teamPoint,
                         Double teamWinRate, Integer teamTotalGameCount, Integer teamWinCount,
                         Integer teamDrawCount, Integer teamLoseCount, String mainArea,
                         LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean applyStatus) {

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
        this.mainArea = mainArea;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.applyStatus = applyStatus;
    }
}
