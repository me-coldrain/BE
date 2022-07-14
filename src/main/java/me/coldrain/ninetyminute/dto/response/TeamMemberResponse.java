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
public class TeamMemberResponse {
    private boolean teamCaptain;
    private CaptainMember captain;
    private List<StrikerMember> striker;
    private List<MidfielderMember> midfielder;
    private List<DefenderMember> defender;
    private List<GoalkeeperMember> goalkeeper;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class CaptainMember {
        private Long memberId;
        private String profileImageUrl;
        private String nickname;
        private int mvpPoint;
        private String position;
        private int positionPoint;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class StrikerMember {
        private Long memberId;
        private String profileImageUrl;
        private String nickname;
        private int mvpPoint;
        private int positionPoint;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class MidfielderMember {
        private Long memberId;
        private String profileImageUrl;
        private String nickname;
        private int mvpPoint;
        private int positionPoint;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class DefenderMember {
        private Long memberId;
        private String profileImageUrl;
        private String nickname;
        private int mvpPoint;
        private int positionPoint;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class GoalkeeperMember {
        private Long memberId;
        private String profileImageUrl;
        private String nickname;
        private int mvpPoint;
        private int positionPoint;
    }
}
