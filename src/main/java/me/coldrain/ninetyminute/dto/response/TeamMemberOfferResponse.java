package me.coldrain.ninetyminute.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TeamMemberOfferResponse {
    private String question;
    private List<OfferedMember> offeredMembers;

    @AllArgsConstructor
    @Getter
    public static class OfferedMember {
        private Long memberId;
        private String profileImageUrl;
        private String nickname;
        private int mvpPoint;
        private String position;
        private int strikerPoint;
        private int midfielderPoint;
        private int defenderPoint;
        private int goalkeeperPoint;
        private String answer;
    }
}
