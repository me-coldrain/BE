package me.coldrain.ninetyminute.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MatchResultRequest {
    private List<MatchMemberRequest> substitutes;
    private Long mvpPlayer; // memberId
    private Long moodMaker; // memberId
    private List<MatchMemberRequest> scorers;
}
