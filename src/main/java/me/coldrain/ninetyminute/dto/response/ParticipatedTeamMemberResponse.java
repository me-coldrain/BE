package me.coldrain.ninetyminute.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParticipatedTeamMemberResponse {

    private Long teamId;
    private List<TeamMember> teamMemberList;

    @Builder
    public ParticipatedTeamMemberResponse(Long teamId, List<TeamMember> teamMemberList) {
        this.teamId = teamId;
        this.teamMemberList = teamMemberList;
    }

    @Getter
    @NoArgsConstructor
    public static class TeamMember {
        private Long memberId;
        private String nickName;

        @Builder
        public TeamMember(Long memberId, String nickName) {
            this.memberId = memberId;
            this.nickName = nickName;
        }

    }
}
