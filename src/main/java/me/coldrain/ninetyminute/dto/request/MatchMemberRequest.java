package me.coldrain.ninetyminute.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchMemberRequest {
    private Long memberId;
    private String position;
    private Boolean anonymous;
}
