package me.coldrain.ninetyminute.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ApprovedMatchResponse {

    private Long matchId;           // approved = true 일 때, applyId
    private Long opposingTeamId;
    private String opposingTeamName;
    private String contact;
    private String phone;
    private Date createdAtMatch;    // 대결이 성사된 날짜
    private Date matchDate;         // 대결이 이뤄 지는 날짜
    private Long dDay;
    private String matchLocation;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public ApprovedMatchResponse(Long matchId, Long opposingTeamId,
                                 String opposingTeamName, String contact,
                                 String phone, Date createdAtMatch, Date matchDate, Long dDay,
                                 String matchLocation, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.matchId = matchId;
        this.opposingTeamId = opposingTeamId;
        this.opposingTeamName = opposingTeamName;
        this.contact = contact;
        this.phone = phone;
        this.createdAtMatch = createdAtMatch;
        this.matchDate = matchDate;
        this.dDay = dDay;
        this.matchLocation = matchLocation;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
