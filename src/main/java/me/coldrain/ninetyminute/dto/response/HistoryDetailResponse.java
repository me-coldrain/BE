package me.coldrain.ninetyminute.dto.response;

import lombok.*;
import me.coldrain.ninetyminute.entity.FieldMember;
import me.coldrain.ninetyminute.entity.Scorer;
import me.coldrain.ninetyminute.entity.SubstituteMember;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class HistoryDetailResponse {
    private Long historyId;
    private Date matchDate;
    private TeamResponse team;
    private TeamResponse opposingTeam;

    @Builder
    public HistoryDetailResponse(Long historyId, Date matchDate, TeamResponse team, TeamResponse opposingTeam) {
        this.historyId = historyId;
        this.matchDate = matchDate;
        this.team = team;
        this.opposingTeam = opposingTeam;
    }

    public void addTeams(TeamResponse team, TeamResponse opposingTeam) {
        this.team = team;
        this.opposingTeam = opposingTeam;
    }

    @Getter
    @NoArgsConstructor
    public static class TeamResponse {
        private String name;
        private String record;
        private Integer score;
        private List<String> scorer;
        private List<MemberResponse> fieldMembers = new ArrayList<>();
        private List<MemberResponse> substituteMembers = new ArrayList<>();

        @Builder
        public TeamResponse(String name, String record, Integer score, List<String> scorer, List<MemberResponse> fieldMembers, List<MemberResponse> substituteMembers) {
            this.name = name;
            this.record = record;
            this.score = score;
            this.scorer = scorer;
            this.fieldMembers = fieldMembers;
            this.substituteMembers = substituteMembers;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        public static class MemberResponse {
            private String nickName;
            private Integer positionPoint;      // 개인의 능력치 총 합
        }

        public void setScorer(List<Scorer> scorerList) {

        }

        public void setFieldMembers(List<FieldMember> fieldMembers, MemberResponse memberResponse) {
            for (FieldMember fieldMember : fieldMembers) {
                memberResponse.setNickName(fieldMember.getMember().getNickname());
                memberResponse.setPositionPoint(fieldMember.getMember().getAbility().getTotalPositionPoint());
                this.fieldMembers.add(memberResponse);
            }
        }

        public void setSubstituteMembers(List<SubstituteMember> substituteMembers, MemberResponse memberResponse) {
            for (SubstituteMember substituteMember : substituteMembers) {
                memberResponse.setNickName(substituteMember.getMember().getNickname());
                memberResponse.setPositionPoint(substituteMember.getMember().getAbility().getTotalPositionPoint());
                this.substituteMembers.add(memberResponse);
            }
        }
    }
}
