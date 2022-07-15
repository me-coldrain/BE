package me.coldrain.ninetyminute.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class fieldMemberRequest {
    private Long memberId;
    private String position;
    private Boolean anonymous;
}
