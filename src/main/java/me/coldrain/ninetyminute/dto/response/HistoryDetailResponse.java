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
        private String mvp;
        private String name;
        private String record;
        private Integer score;
        private List<String> scorer;
        private List<List<MemberResponse>> fieldMembers = new ArrayList<>();
        private List<List<MemberResponse>> substituteMembers = new ArrayList<>();

        @Builder
        public TeamResponse(String mvp, String name, String record, Integer score, List<String> scorer, List<List<MemberResponse>> fieldMembers, List<List<MemberResponse>> substituteMembers) {
            this.mvp = mvp;
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
            for (Scorer scorerMember : scorerList) {
                if (!scorerMember.getFieldMember().getAnonymous()) {
                    this.scorer.add(scorerMember.getFieldMember().getMember().getNickname());
                } else {
                    this.scorer.add("비회원");
                }
                if (!scorerMember.getSubstituteMember().getAnonymous()) {
                    this.scorer.add(scorerMember.getSubstituteMember().getMember().getNickname());
                } else {
                    this.scorer.add("비회원");
                }
            }
        }

        public void setFieldMembers(List<FieldMember> fieldMembers, MemberResponse memberResponse) {
            List<MemberResponse> strikers = new ArrayList<>();
            List<MemberResponse> midfielders = new ArrayList<>();
            List<MemberResponse> defenders = new ArrayList<>();
            List<MemberResponse> goalkeeper = new ArrayList<>();

            for (FieldMember fieldMember : fieldMembers) {
                if (!fieldMember.getAnonymous()) {
                    switch (fieldMember.getPosition()) {
                        case "striker":
                            memberResponse.setNickName(fieldMember.getMember().getNickname());
                            memberResponse.setPositionPoint(fieldMember.getMember().getAbility().getTotalPositionPoint());
                            strikers.add(memberResponse);
                            break;
                        case "midfielder":
                            memberResponse.setNickName(fieldMember.getMember().getNickname());
                            memberResponse.setPositionPoint(fieldMember.getMember().getAbility().getTotalPositionPoint());
                            midfielders.add(memberResponse);
                            break;
                        case "defender":
                            memberResponse.setNickName(fieldMember.getMember().getNickname());
                            memberResponse.setPositionPoint(fieldMember.getMember().getAbility().getTotalPositionPoint());
                            defenders.add(memberResponse);
                            break;
                        default:
                            memberResponse.setNickName(fieldMember.getMember().getNickname());
                            memberResponse.setPositionPoint(fieldMember.getMember().getAbility().getTotalPositionPoint());
                            goalkeeper.add(memberResponse);
                            break;
                    }
                } else {
                    switch (fieldMember.getPosition()) {
                        case "striker":
                            memberResponse.setNickName("비회원");
                            memberResponse.setPositionPoint(null);
                            strikers.add(memberResponse);
                            break;
                        case "midfielder":
                            memberResponse.setNickName("비회원");
                            memberResponse.setPositionPoint(null);
                            midfielders.add(memberResponse);
                            break;
                        case "defender":
                            memberResponse.setNickName("비회원");
                            memberResponse.setPositionPoint(null);
                            defenders.add(memberResponse);
                            break;
                        default:
                            memberResponse.setNickName("비회원");
                            memberResponse.setPositionPoint(null);
                            goalkeeper.add(memberResponse);
                            break;
                    }
                }
            }
            this.fieldMembers.add(strikers);
            this.fieldMembers.add(midfielders);
            this.fieldMembers.add(defenders);
            this.fieldMembers.add(goalkeeper);
        }

        public void setSubstituteMembers(List<SubstituteMember> substituteMembers, MemberResponse memberResponse) {
            List<MemberResponse> strikers = new ArrayList<>();
            List<MemberResponse> midfielders = new ArrayList<>();
            List<MemberResponse> defenders = new ArrayList<>();
            List<MemberResponse> goalkeeper = new ArrayList<>();

            for (SubstituteMember substituteMember : substituteMembers) {
                if (!substituteMember.getAnonymous()) {
                    switch (substituteMember.getPosition()) {
                        case "striker":
                            memberResponse.setNickName(substituteMember.getMember().getNickname());
                            memberResponse.setPositionPoint(substituteMember.getMember().getAbility().getTotalPositionPoint());
                            strikers.add(memberResponse);
                            break;
                        case "midfielder":
                            memberResponse.setNickName(substituteMember.getMember().getNickname());
                            memberResponse.setPositionPoint(substituteMember.getMember().getAbility().getTotalPositionPoint());
                            midfielders.add(memberResponse);
                            break;
                        case "defender":
                            memberResponse.setNickName(substituteMember.getMember().getNickname());
                            memberResponse.setPositionPoint(substituteMember.getMember().getAbility().getTotalPositionPoint());
                            defenders.add(memberResponse);
                            break;
                        default:
                            memberResponse.setNickName(substituteMember.getMember().getNickname());
                            memberResponse.setPositionPoint(substituteMember.getMember().getAbility().getTotalPositionPoint());
                            goalkeeper.add(memberResponse);
                            break;
                    }
                } else {
                    switch (substituteMember.getPosition()) {
                        case "striker":
                            memberResponse.setNickName("비회원");
                            memberResponse.setPositionPoint(null);
                            strikers.add(memberResponse);
                            break;
                        case "midfielder":
                            memberResponse.setNickName("비회원");
                            memberResponse.setPositionPoint(null);
                            midfielders.add(memberResponse);
                            break;
                        case "defender":
                            memberResponse.setNickName("비회원");
                            memberResponse.setPositionPoint(null);
                            defenders.add(memberResponse);
                            break;
                        default:
                            memberResponse.setNickName("비회원");
                            memberResponse.setPositionPoint(null);
                            goalkeeper.add(memberResponse);
                            break;
                    }
                }
                this.substituteMembers.add(strikers);
                this.substituteMembers.add(midfielders);
                this.substituteMembers.add(defenders);
                this.substituteMembers.add(goalkeeper);
            }
        }
    }
}
