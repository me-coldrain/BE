package me.coldrain.ninetyminute.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class MatchResponse {

    private Long matchId;
    private Boolean isCaptain;
    private Long opposingTeamId;
    private String opposingTeamName;
    private Integer opposingTeamMemberCount;
    private Integer opposingTeamPoint;
    private Double opposingTeamWinRate;
    private Integer opposingTeamTotalGameCount;
    private Integer opposingTeamWinCount;
    private Integer opposingTeamDrawCount;
    private Integer opposingTeamLoseCount;
    private String contact;
    private String phone;
    private Date matchDate;         // 대결이 이뤄 지는 날짜
    private Long dDay;
    private String matchLocation;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private Boolean matchStatus;

    public void changeIsCaptain (Boolean isCaptain) {
        this.isCaptain = isCaptain;
    }

    @Builder
    public MatchResponse(Long matchId, Boolean isCaptain, Long opposingTeamId,
                         String opposingTeamName, Integer opposingTeamMemberCount, Integer opposingTeamPoint, Double opposingTeamWinRate,
                         Integer opposingTeamTotalGameCount, Integer opposingTeamWinCount,
                         Integer opposingTeamDrawCount, Integer opposingTeamLoseCount,
                         String contact, String phone, Date matchDate, Long dDay,
                         String matchLocation, LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean matchStatus) {
        this.matchId = matchId;
        this.isCaptain = isCaptain;
        this.opposingTeamId = opposingTeamId;
        this.opposingTeamName = opposingTeamName;
        this.opposingTeamMemberCount = opposingTeamMemberCount;
        this.opposingTeamPoint = opposingTeamPoint;
        this.opposingTeamWinRate = opposingTeamWinRate;
        this.opposingTeamTotalGameCount = opposingTeamTotalGameCount;
        this.opposingTeamWinCount = opposingTeamWinCount;
        this.opposingTeamDrawCount = opposingTeamDrawCount;
        this.opposingTeamLoseCount = opposingTeamLoseCount;
        this.contact = contact;
        this.phone = phone;
        this.matchDate = matchDate;
        this.dDay = dDay;
        this.matchLocation = matchLocation;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.matchStatus = matchStatus;
    }
}