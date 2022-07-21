package me.coldrain.ninetyminute.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.coldrain.ninetyminute.dto.request.MatchMemberRequest;

import java.util.List;

@Getter
@NoArgsConstructor
public class MatchMemberResponse {

    private Long matchId;
    private List<MatchMemberRequest> fieldMembers;

    @Builder
    public MatchMemberResponse(Long matchId, List<MatchMemberRequest> fieldMembers) {
        this.matchId = matchId;
        this.fieldMembers = fieldMembers;
    }
}
