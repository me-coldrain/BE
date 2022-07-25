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
        private String teamName;
        private String record;
        private Integer score;
        private List<String> scorer = new ArrayList<>();
        private PlayerResponse fieldMembers;
        private PlayerResponse substituteMembers;

        @Builder
        public TeamResponse(String mvp, String teamName, String record, Integer score, List<String> scorer, PlayerResponse fieldMembers, PlayerResponse substituteMembers) {
            this.mvp = mvp;
            this.teamName = teamName;
            this.record = record;
            this.score = score;
            this.scorer = scorer;
            this.fieldMembers = fieldMembers;
            this.substituteMembers = substituteMembers;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        public static class PlayerResponse {
            private List<MemberResponse> striker = new ArrayList<>();
            private List<MemberResponse> midfielder = new ArrayList<>();
            private List<MemberResponse> defender = new ArrayList<>();
            private List<MemberResponse> goalkeeper = new ArrayList<>();

            private void resetPlayerResponse(PlayerResponse playerResponse) {
                playerResponse.setStriker(null);
                playerResponse.setMidfielder(null);
                playerResponse.setDefender(null);
                playerResponse.setGoalkeeper(null);
            }
        }

        @Getter
        @Builder
        @AllArgsConstructor
        public static class MemberResponse {
            private Long memberId;
            private String nickName;
            private String memberProfileUrl;
            private Integer positionPoint;      // 개인의 능력치 총 합

            public MemberResponse changeMemberResponse(Long memberId, String nickName, String memberProfileUrl, Integer positionPoint) {
                return MemberResponse.builder()
                        .memberId(memberId)
                        .nickName(nickName)
                        .memberProfileUrl(memberProfileUrl)
                        .positionPoint(positionPoint)
                        .build();
            }

        }

        public List<String> updateScorer(List<Scorer> scorerList, List<String> scorer) {
            scorer.clear();
            for (Scorer scorerMember : scorerList) {
                if (scorerMember.getFieldMember() != null) {
                    if (!scorerMember.getFieldMember().getAnonymous()) {
                        scorer.add(scorerMember.getFieldMember().getMember().getNickname());
                    }
                } else if (scorerMember.getSubstituteMember() != null) {
                    if (!scorerMember.getSubstituteMember().getAnonymous()) {
                        scorer.add(scorerMember.getSubstituteMember().getMember().getNickname());
                    }
                } else scorer.add("비회원");
            }
            return scorer;
        }

        public void setScorer(List<String> scorer) {
            this.scorer = scorer;
        }

        public void setFieldMembers(List<FieldMember> fieldMemberList, MemberResponse memberResponse, PlayerResponse fieldMembers) {
//            fieldMembers.resetPlayerResponse(fieldMembers);
            List<MemberResponse> strikers = new ArrayList<>();
            List<MemberResponse> midfielders = new ArrayList<>();
            List<MemberResponse> defenders = new ArrayList<>();
            List<MemberResponse> goalkeeper = new ArrayList<>();
            MemberResponse tempMember = new MemberResponse(null, null, null, null);

            for (FieldMember fieldMember : fieldMemberList) {
                switch (fieldMember.getPosition()) {
                    case "striker":
                        if (fieldMember.getAnonymous()) {
                            tempMember = memberResponse.changeMemberResponse(null, "비회원", null, null);
                        } else {
                            tempMember = memberResponse.changeMemberResponse(fieldMember.getMember().getId(),
                                    fieldMember.getMember().getNickname(),
                                    fieldMember.getMember().getProfileUrl(),
                                    fieldMember.getMember().getAbility().getTotalPositionPoint());
                        }
                        strikers.add(tempMember);
                        break;
                    case "midfielder":
                        if (fieldMember.getAnonymous()) {
                            tempMember = memberResponse.changeMemberResponse(null, "비회원", null, null);
                        } else {
                            tempMember = memberResponse.changeMemberResponse(fieldMember.getMember().getId(),
                                    fieldMember.getMember().getNickname(),
                                    fieldMember.getMember().getProfileUrl(),
                                    fieldMember.getMember().getAbility().getTotalPositionPoint());
                        }
                        midfielders.add(tempMember);
                        break;
                    case "defender":
                        if (fieldMember.getAnonymous()) {
                            tempMember = memberResponse.changeMemberResponse(null, "비회원", null, null);
                        } else {
                            tempMember = memberResponse.changeMemberResponse(fieldMember.getMember().getId(),
                                    fieldMember.getMember().getNickname(),
                                    fieldMember.getMember().getProfileUrl(),
                                    fieldMember.getMember().getAbility().getTotalPositionPoint());
                        }
                        defenders.add(tempMember);
                        break;
                    case "goalkeeper":
                        if (fieldMember.getAnonymous()) {
                            tempMember = memberResponse.changeMemberResponse(null, "비회원", null, null);
                        } else {
                            tempMember = memberResponse.changeMemberResponse(fieldMember.getMember().getId(),
                                    fieldMember.getMember().getNickname(),
                                    fieldMember.getMember().getProfileUrl(),
                                    fieldMember.getMember().getAbility().getTotalPositionPoint());
                        }
                        goalkeeper.add(tempMember);
                        break;
                }
            }
            fieldMembers.setStriker(strikers);
            fieldMembers.setMidfielder(midfielders);
            fieldMembers.setDefender(defenders);
            fieldMembers.setGoalkeeper(goalkeeper);
            this.fieldMembers = fieldMembers;
        }

        public void setSubstituteMembers(List<SubstituteMember> substituteMemberList, MemberResponse memberResponse, PlayerResponse substituteMembers) {
//            substituteMembers.resetPlayerResponse(substituteMembers);
            List<MemberResponse> strikers = new ArrayList<>();
            List<MemberResponse> midfielders = new ArrayList<>();
            List<MemberResponse> defenders = new ArrayList<>();
            List<MemberResponse> goalkeeper = new ArrayList<>();
            MemberResponse tempMember = new MemberResponse(null, null, null, null);

            for (SubstituteMember substituteMember : substituteMemberList) {
                switch (substituteMember.getPosition()) {
                    case "striker":
                        if (substituteMember.getAnonymous()) {
                            tempMember = memberResponse.changeMemberResponse(null, "비회원", null, null);
                        } else {
                            tempMember = memberResponse.changeMemberResponse(substituteMember.getMember().getId(),
                                    substituteMember.getMember().getNickname(),
                                    substituteMember.getMember().getProfileUrl(),
                                    substituteMember.getMember().getAbility().getTotalPositionPoint());
                        }
                        strikers.add(tempMember);
                        break;
                    case "midfielder":
                        if (substituteMember.getAnonymous()) {
                            tempMember = memberResponse.changeMemberResponse(null, "비회원", null, null);
                        } else {
                            tempMember = memberResponse.changeMemberResponse(substituteMember.getMember().getId(),
                                    substituteMember.getMember().getNickname(),
                                    substituteMember.getMember().getProfileUrl(),
                                    substituteMember.getMember().getAbility().getTotalPositionPoint());
                        }
                        midfielders.add(tempMember);
                        break;
                    case "defender":
                        if (substituteMember.getAnonymous()) {
                            tempMember = memberResponse.changeMemberResponse(null, "비회원", null, null);
                        } else {
                            tempMember = memberResponse.changeMemberResponse(substituteMember.getMember().getId(),
                                    substituteMember.getMember().getNickname(),
                                    substituteMember.getMember().getProfileUrl(),
                                    substituteMember.getMember().getAbility().getTotalPositionPoint());
                        }
                        defenders.add(tempMember);
                        break;
                    case "goalkeeper":
                        if (substituteMember.getAnonymous()) {
                            tempMember = memberResponse.changeMemberResponse(null, "비회원", null, null);
                        } else {
                            tempMember = memberResponse.changeMemberResponse(substituteMember.getMember().getId(),
                                    substituteMember.getMember().getNickname(),
                                    substituteMember.getMember().getProfileUrl(),
                                    substituteMember.getMember().getAbility().getTotalPositionPoint());
                        }
                        goalkeeper.add(tempMember);
                        break;
                }
            }
            substituteMembers.setStriker(strikers);
            substituteMembers.setMidfielder(midfielders);
            substituteMembers.setDefender(defenders);
            substituteMembers.setGoalkeeper(goalkeeper);
            this.substituteMembers = substituteMembers;
        }
    }
}
