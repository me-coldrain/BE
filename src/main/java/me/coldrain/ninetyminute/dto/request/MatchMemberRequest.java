package me.coldrain.ninetyminute.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchMemberRequest {
    private Long memberId;
    private String position;
    private Boolean anonymous;

    @Builder
    public MatchMemberRequest(Long memberId, String position, Boolean anonymous) {
        this.memberId = memberId;
        this.position = position;
        this.anonymous = anonymous;
    }
}
