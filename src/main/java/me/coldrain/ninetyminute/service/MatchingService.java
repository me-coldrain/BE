package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.request.ApprovedMatchRequest;
import me.coldrain.ninetyminute.dto.request.MatchMemberRequest;
import me.coldrain.ninetyminute.dto.request.MatchResultRequest;
import me.coldrain.ninetyminute.dto.request.MatchScoreRequest;
import me.coldrain.ninetyminute.dto.response.MatchMemberResponse;
import me.coldrain.ninetyminute.dto.response.MatchResponse;
import me.coldrain.ninetyminute.dto.response.OfferMatchResponse;
import me.coldrain.ninetyminute.dto.response.ParticipationTeamMatchResponse;
import me.coldrain.ninetyminute.entity.*;
import me.coldrain.ninetyminute.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingService {

    private final MemberRepository memberRepository;
    private final ParticipationRepository participationRepository;
    private final TeamRepository teamRepository;
    private final ApplyRepository applyRepository;
    private final BeforeMatchingRepository beforeMatchingRepository;
    private final FieldMemberRepository fieldMemberRepository;
    private final AfterMatchingRepository afterMatchingRepository;
    private final SubstituteRepository substituteRepository;
    private final ScorerRepository scorerRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public String approveApplyMatch(Long applyTeamId, Long applyId, ApprovedMatchRequest approvedMatchRequest, Member member) {
        if (!member.getOpenTeam().getId().equals(applyTeamId)) {
            String teamName = teamRepository.findById(member.getOpenTeam().getId()).orElseThrow(
                    () -> new IllegalArgumentException("팀을 찾을 수 없습니다.")).getName();

            String opposingTeamName = teamRepository.findById(applyTeamId).orElseThrow(
                    () -> new IllegalArgumentException("팀을 찾을 수 없습니다.")).getName();

            Apply applyMatch = applyRepository.findById(applyId).orElseThrow(
                    () -> new IllegalArgumentException("신청한 대결을 찾을 수 없습니다.")
            );

            if (!applyMatch.getApproved()) {
                applyMatch.changeApproved(true);

                BeforeMatching beforeMatching = BeforeMatching.builder()
                        .apply(applyMatch)
                        .matchDate(approvedMatchRequest.getMatchDate())
                        .location(approvedMatchRequest.getMatchLocation())
                        .opposingTeamName(opposingTeamName)
                        .teamName(teamName)
                        .build();

                beforeMatchingRepository.save(beforeMatching);
                return "완료됐습니다.";
            } else throw new IllegalArgumentException("이미 이 대결은 수락되었습니다.");
        } else throw new IllegalArgumentException("자신의 팀에 대결 수락은 불가능합니다.");
    }

    @Transactional
    public void rejectApplyMatch(Long applyTeamId, Long applyId, Member member) {
        if (!member.getOpenTeam().getId().equals(applyTeamId)) {
            Apply applyMatch = applyRepository.findById(applyId).orElseThrow(
                    () -> new IllegalArgumentException("신청한 대결이 존재하지 않습니다.")
            );
            if (applyMatch.getApproved()) {
                applyRepository.delete(applyMatch);
            } else throw new IllegalArgumentException("성사된 대결이 아니므로, 해당 대결이 존재하지 않아 거절할 수 없습니다.");
        } else throw new IllegalArgumentException("같은 팀 끼리의 대결은 존재 하지 않습니다.");
    }


    public List<OfferMatchResponse> searchOfferMatches(Long teamId, Member member) {
        List<OfferMatchResponse> offerMatchResponseList = new ArrayList<>();
        if (member.getOpenTeam().getId().equals(teamId)) {
            List<Apply> applyList = applyRepository.findAllByTeamIdOrderByCreatedDate(teamId);
            // 해당 팀에 신청된 경기가 없어도 null이 전달되야한다.
            if (!applyList.isEmpty()) {
                for (Apply apply : applyList) {
                    Member opposingTeamCaptain = memberRepository.findByOpenTeam(apply.getApplyTeam().getId()).orElseThrow(
                            () -> new IllegalArgumentException("해당 팀이 존재 하지 않습니다."));
                    OfferMatchResponse offerMatchResponse = OfferMatchResponse.builder()
                            .applyId(apply.getId())
                            .opposingTeamId(apply.getApplyTeam().getId())
                            .opposingTeamName(apply.getApplyTeam().getName())
                            .contact(opposingTeamCaptain.getContact())
                            .phone(opposingTeamCaptain.getPhone())
                            .opposingTeamPoint(apply.getApplyTeam().getRecord().getWinPoint())
                            .winRate(apply.getApplyTeam().getRecord().getWinRate())
                            .greeting(apply.getGreeting())
                            .createdDate(apply.getCreatedDate())
                            .modifiedDate(apply.getModifiedDate())
                            .build();
                    offerMatchResponseList.add(offerMatchResponse);
                }
            }
            return offerMatchResponseList;
        } else throw new IllegalArgumentException("이 팀의 주장이 아님니다.");
    }

    public List<MatchResponse> searchApplyMatch(Long teamId, Member member) {
        if (member.getOpenTeam().getId().equals(teamId)) {
            List<Apply> applyList = applyRepository.findAllByApplyTeamIdOrderByCreatedDate(teamId);    // approved = false 일 때만
            List<MatchResponse> matchResponseList = new ArrayList<>();

            for (Apply apply : applyList) {
                Team opposingTeam = apply.getTeam();
                if (apply.getApproved()) {
                    BeforeMatching beforeMatching = beforeMatchingRepository.findByApplyIdApprovedTrue(apply.getId()).orElseThrow(
                            () -> new IllegalArgumentException("성사된 대결이 존재하지 않습니다."));
                    MatchResponse matchResponse = MatchResponse.builder()
                            .matchId(beforeMatching.getId())
                            .opposingTeamId(opposingTeam.getId())
                            .opposingTeamName(opposingTeam.getName())
                            .opposingTeamMemberCount(participationRepository.findAllByTeamIdTrue(opposingTeam.getId()).size())
                            .opposingTeamPoint(opposingTeam.getRecord().getWinPoint())
                            .opposingTeamTotalGameCount(opposingTeam.getRecord().getTotalGameCount())
                            .opposingTeamWinRate(opposingTeam.getRecord().getWinRate())
                            .opposingTeamWinCount(opposingTeam.getRecord().getWinCount())
                            .opposingTeamDrawCount(opposingTeam.getRecord().getDrawCount())
                            .opposingTeamLoseCount(opposingTeam.getRecord().getLoseCount())
                            .matchLocation(beforeMatching.getLocation())
                            .createdDate(beforeMatching.getCreatedDate())
                            .modifiedDate(beforeMatching.getModifiedDate())
                            .matchStatus(true)
                            .build();
                    matchResponseList.add(matchResponse);
                } else {
                    MatchResponse matchResponse = MatchResponse.builder()
                            .matchId(null)
                            .opposingTeamId(opposingTeam.getId())
                            .opposingTeamName(opposingTeam.getName())
                            .opposingTeamMemberCount(participationRepository.findAllByTeamIdTrue(opposingTeam.getId()).size())
                            .opposingTeamPoint(opposingTeam.getRecord().getWinPoint())
                            .opposingTeamTotalGameCount(opposingTeam.getRecord().getTotalGameCount())
                            .opposingTeamWinRate(opposingTeam.getRecord().getWinRate())
                            .opposingTeamWinCount(opposingTeam.getRecord().getWinCount())
                            .opposingTeamDrawCount(opposingTeam.getRecord().getDrawCount())
                            .opposingTeamLoseCount(opposingTeam.getRecord().getLoseCount())
                            .matchLocation(opposingTeam.getMainArea())
                            .createdDate(apply.getCreatedDate())
                            .modifiedDate(apply.getModifiedDate())
                            .matchStatus(false)
                            .build();
                    matchResponseList.add(matchResponse);
                }
            }
            return matchResponseList;
        } else throw new IllegalArgumentException("이 팀의 주장이 아닙니다.");
    }

    public List<ParticipationTeamMatchResponse> searchMatches(Long memberId, Member member) {
        List<Participation> ParticipationList = participationRepository.findAllByMemberIdTrue(memberId);
        List<ParticipationTeamMatchResponse> participationTeamMatchResponseList = new ArrayList<>();

        for (Participation participation : ParticipationList) {
            List<BeforeMatching> beforeMatchingList = beforeMatchingRepository.forMatchingList(participation.getTeam().getName());
            Boolean isCaptain = memberRepository.findById(memberId).orElseThrow(
                    () -> new IllegalArgumentException("이 회원은 존재하지 않습니다.")).getOpenTeam().getId().equals(participation.getTeam().getId());
            Integer teamMemberCnt = participationRepository.findAllByTeamIdTrue(participation.getTeam().getId()).size();
            ParticipationTeamMatchResponse participationTeamMatchResponse = ParticipationTeamMatchResponse.builder()
                    .matchId(null)
                    .isCaptain(isCaptain)
                    .teamName(participation.getTeam().getName())
                    .teamMemberCount(teamMemberCnt)
                    .teamPoint(participation.getTeam().getRecord().getWinPoint())
                    .teamWinRate(participation.getTeam().getRecord().getWinRate())
                    .teamTotalGameCount(participation.getTeam().getRecord().getTotalGameCount())
                    .teamWinCount(participation.getTeam().getRecord().getWinCount())
                    .teamDrawCount(participation.getTeam().getRecord().getDrawCount())
                    .teamLoseCount(participation.getTeam().getRecord().getLoseCount())
                    .matchLocation(participation.getTeam().getMainArea())
                    .createdDate(participation.getCreatedDate())
                    .modifiedDate(participation.getModifiedDate())
                    .matchStatus(false)
                    .build();
            if (!beforeMatchingList.isEmpty()) {
                for (BeforeMatching beforeMatching : beforeMatchingList) {
                    participationTeamMatchResponse.changeMatchId(beforeMatching.getId());
                    participationTeamMatchResponse.changeMatchLocation(beforeMatching.getLocation());
                    participationTeamMatchResponse.changeMatchStatus(true);
                    participationTeamMatchResponseList.add(participationTeamMatchResponse);
                }
            }
            participationTeamMatchResponseList.add(participationTeamMatchResponse);
        }
        return participationTeamMatchResponseList;
    }

    public MatchResponse searchApprovedMatchDetail(Long teamId, Long applyId, Member member) {
        Participation participation = participationRepository.findByMemberIdAndTeamIdTrue(member.getId(), teamId).orElse(null);

        if (participation != null || member.getOpenTeam().getId().equals(teamId)) { // 팀의 멤버인지 확인, 주장일 때 participation 에 등록 되지 않는 것을 고려해야한다.
            BeforeMatching beforeMatching = beforeMatchingRepository.findByApplyIdApprovedTrue(applyId).orElseThrow(
                    () -> new IllegalArgumentException("해당 대결을 찾지 못했습니다."));
            Member captainMember = memberRepository.findByOpenTeam(beforeMatching.getApply().getApplyTeam().getId()).orElseThrow(() -> new IllegalAccessError(
                    "해당 멤버을 찾을 수 없습니다."));
            Team opposingTeam = beforeMatching.getApply().getApplyTeam();
            LocalDateTime from = LocalDateTime.now();
            LocalDateTime to = LocalDateTime.ofInstant(beforeMatching.getMatchDate().toInstant(), ZoneId.systemDefault());
            MatchResponse matchResponse = MatchResponse.builder()
                    .matchId(beforeMatching.getId())
                    .isCaptain(false)
                    .opposingTeamId(opposingTeam.getId())
                    .opposingTeamName(opposingTeam.getName())
                    .opposingTeamPoint(opposingTeam.getRecord().getWinPoint())
                    .opposingTeamTotalGameCount(opposingTeam.getRecord().getTotalGameCount())
                    .opposingTeamWinRate(opposingTeam.getRecord().getWinRate())
                    .opposingTeamWinCount(opposingTeam.getRecord().getWinCount())
                    .opposingTeamDrawCount(opposingTeam.getRecord().getDrawCount())
                    .opposingTeamLoseCount(opposingTeam.getRecord().getLoseCount())
                    .contact(captainMember.getContact())
                    .phone(captainMember.getPhone())
                    .matchDate(beforeMatching.getMatchDate())
                    .dDay(ChronoUnit.DAYS.between(from, to))
                    .matchLocation(beforeMatching.getLocation())
                    .createdDate(beforeMatching.getCreatedDate())
                    .modifiedDate(beforeMatching.getModifiedDate())
                    .build();
            if (member.getOpenTeam().getId().equals(teamId)) { // team의 주장의 경우
                matchResponse.changeIsCaptain(true);
            }
            return matchResponse;
        } else throw new IllegalArgumentException("이 팀의 멤버가 아닙니다.");
    }

    @Transactional
    public void makeTeamFormation(Long teamId, Long matchId, List<MatchMemberRequest> matchMemberRequestList, Member member) {
        if (matchMemberRequestList.size() > 4) {
            BeforeMatching beforeMatching = beforeMatchingRepository.findById(matchId).orElseThrow(
                    () -> new IllegalArgumentException("해당 대결 정보가 존재하지 않습니다.")
            );
            if (teamId.equals(beforeMatching.getApply().getTeam().getId()) || teamId.equals(beforeMatching.getApply().getApplyTeam().getId())) {
                Team team = teamRepository.findById(member.getOpenTeam().getId()).orElseThrow(
                        () -> new IllegalArgumentException("해당 팀을 찾을 수 없습니다.")
                );
                for (MatchMemberRequest matchMemberRequest : matchMemberRequestList) {
                    FieldMember fieldMember = FieldMember.builder()
                            .position(matchMemberRequest.getPosition())
                            .anonymous(matchMemberRequest.getAnonymous())
                            .member(null)
                            .team(team)
                            .beforeMatching(beforeMatching)
                            .afterMatching(null)
                            .build();
                    if (!matchMemberRequest.getAnonymous()) {
                        Member registerMember = memberRepository.findById(matchMemberRequest.getMemberId()).orElseThrow(
                                () -> new IllegalArgumentException("해당 맴버를 찾을 수 없습니다."));
                        fieldMember.changeMember(registerMember);
                    }
                    fieldMemberRepository.save(fieldMember);
                }
            } else throw new IllegalArgumentException("해당 팀은 성사된 대결의 팀이 아닙니다.");
        } else throw new IllegalArgumentException("최소 5명 이상의 선발 선수를 등록해 주세요.");
    }

    public MatchMemberResponse showFormation(Long teamId, Long matchId, Member member) {
        List<FieldMember> fieldMemberList = fieldMemberRepository.findAllByMatchFieldMembers(teamId, matchId);
        List<MatchMemberRequest> fieldMembers = new ArrayList<>();
        for (FieldMember fieldMember : fieldMemberList) {
            MatchMemberRequest matchMemberRequest = MatchMemberRequest.builder()
                    .memberId(null)
                    .memberProfileUrl(null)
                    .position(fieldMember.getPosition())
                    .anonymous(fieldMember.getAnonymous())
                    .build();
            if (!fieldMember.getAnonymous()) {
                matchMemberRequest.setMemberId(fieldMember.getMember().getId());
                matchMemberRequest.setMemberProfileUrl(fieldMember.getMember().getProfileUrl());
            }
            fieldMembers.add(matchMemberRequest);
        }
        return MatchMemberResponse.builder()
                .matchId(matchId)
                .fieldMembers(fieldMembers)
                .build();
    }

    @Transactional
    public void cancelApprovedMatch(Long matchId, Member member) {
        BeforeMatching beforeMatching = beforeMatchingRepository.findById(matchId).orElseThrow(
                () -> new IllegalArgumentException("해당 대결 정보가 존재하지 않습니다."));
        if (member.getOpenTeam().getId().equals(beforeMatching.getApply().getTeam().getId()) || member.getOpenTeam().getId().equals(beforeMatching.getApply().getApplyTeam().getId())) {
            applyRepository.delete(beforeMatching.getApply());
            beforeMatchingRepository.delete(beforeMatching);
        } else throw new IllegalArgumentException("해당 팀의 주장이 아닙니다.");
    }

    @Transactional
    public void confirmEndMatch(Long matchId, Member member) {
        BeforeMatching beforeMatching = beforeMatchingRepository.findById(matchId).orElseThrow(
                () -> new IllegalArgumentException("찾는 대결이 존재하지 않습니다.")
        );

        if (member.getOpenTeam().getId().equals(beforeMatching.getApply().getTeam().getId())) {
            beforeMatching.getApply().changeEndMatchStatus(true);
        } else if (member.getOpenTeam().getId().equals(beforeMatching.getApply().getApplyTeam().getId())) {
            beforeMatching.getApply().changeOpposingTeamEndMatchStatus(true);
        } else throw new IllegalArgumentException("해당 대결 진행 중인 팀의 주장이 아닙니다.");
    }

    @Transactional
    public void writeMatchScore(Long matchId, MatchScoreRequest matchScoreRequest, Member member) {
        BeforeMatching beforeMatching = beforeMatchingRepository.findById(matchId).orElseThrow(
                () -> new IllegalArgumentException("해당 대결을 찾을 수 없습니다."));
        if (member.getOpenTeam().getId().equals(beforeMatching.getApply().getTeam().getId())) {
            AfterMatching afterMatching = AfterMatching.builder()
                    .beforeMatching(beforeMatching)
                    .mvpNickname(null)
                    .moodMaker(null)
                    .score(matchScoreRequest.getTeamScore())
                    .opponentScore(matchScoreRequest.getOpponentScore())
                    .admitStatus(false)
                    .build();
            afterMatchingRepository.save(afterMatching);
        } else throw new IllegalArgumentException("해당 팀의 주장이 아닙니다.");
    }

    @Transactional
    public void confirmMatchScore(Long matchId, Member member) {
        BeforeMatching beforeMatching = beforeMatchingRepository.findById(matchId).orElseThrow(
                () -> new IllegalArgumentException("성사된 대결이 존재하지 않습니다."));
        if (member.getOpenTeam().getId().equals(beforeMatching.getApply().getTeam().getId()) || member.getOpenTeam().getId().equals(beforeMatching.getApply().getApplyTeam().getId())) {
            AfterMatching afterMatching = afterMatchingRepository.findByBeforeMatchingIdAdmitStatusFalse(matchId).orElseThrow(
                    () -> new IllegalArgumentException("해당 대결을 찾을 수 없습니다.")
            );
            afterMatching.changeAdmitStatus(true);
        } else throw new IllegalArgumentException("해당 팀의 주장이 아닙니다.");
    }

    @Transactional
    public void correctMatchScore(Long matchId, MatchScoreRequest matchScoreRequest, Member member) {
        BeforeMatching beforeMatching = beforeMatchingRepository.findById(matchId).orElseThrow(
                () -> new IllegalArgumentException("성사된 대결이 존재하지 않습니다."));
        if (member.getOpenTeam().getId().equals(beforeMatching.getApply().getApplyTeam().getId())) {
            AfterMatching afterMatching = afterMatchingRepository.findByBeforeMatchingIdAdmitStatusFalse(matchId).orElseThrow(
                    () -> new IllegalArgumentException("해댱 대결을 찾을 수 없습니다.")
            );
            afterMatching.correctScore(matchScoreRequest.getTeamScore(), matchScoreRequest.getOpponentScore());
        } else throw new IllegalArgumentException("해당 팀의 주장이 아닙니다.");
    }

    @Transactional
    public void writeMatchResult(Long matchId, MatchResultRequest matchResultRequest, Member member) {
        Participation participation = participationRepository.findByTeamIdAndMemberId(member.getOpenTeam().getId(), member.getId()).orElse(null);
        BeforeMatching beforeMatching = beforeMatchingRepository.findById(matchId).orElseThrow(
                () -> new IllegalArgumentException("성사된 대결이 존재하지 않습니다."));
        AfterMatching afterMatching = afterMatchingRepository.findByBeforeMatchingIdAdmitStatusTrue(matchId).orElseThrow(
                () -> new IllegalArgumentException("해당 대결을 찾을 수 없습니다."));
        List<FieldMember> fieldMembers = fieldMemberRepository.findAllByMatchFieldMembers(member.getOpenTeam().getId(), beforeMatching.getId());
        fieldMembers.forEach(fieldMember -> fieldMember.setAfterMatching(afterMatching));
        List<SubstituteMember> substituteMembers = new ArrayList<>();

        if (afterMatching.getAdmitStatus()) {
            if (participation != null) {
                for (MatchMemberRequest substitute : matchResultRequest.getSubstitutes()) {
                    SubstituteMember substituteMember = SubstituteMember.builder()
                            .member(null)
                            .team(member.getOpenTeam())
                            .afterMatching(afterMatching)
                            .position(substitute.getPosition())
                            .anonymous(true)
                            .build();
                    if (!substitute.getAnonymous()) {
                        substituteMember.setMember(memberRepository.findById(substitute.getMemberId()).orElseThrow(
                                () -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다.")));
                        substituteMember.changeAnonymous(substitute.getAnonymous());
                    }
                    substituteRepository.save(substituteMember);
                    substituteMembers.add(substituteMember);
                }
                if (member.getOpenTeam().getId().equals(beforeMatching.getApply().getTeam().getId())) {
                    if (afterMatching.getScore() == matchResultRequest.getScorers().size()) { // home team
                        for (MatchMemberRequest scorer : matchResultRequest.getScorers()) {
                            if (!scorer.getAnonymous()) {
                                FieldMember fieldMember = fieldMemberRepository.findByMemberIdAndTeamId(scorer.getMemberId(), member.getOpenTeam().getId()).orElse(null);
                                SubstituteMember substituteMember = searchSubMember(substituteMembers, scorer.getMemberId());
                                if (fieldMember == null && substituteMember == null) throw new IllegalArgumentException("해당 선수는 팀에 소속되어 있지 않습니다.");
                                Scorer scorerMember = Scorer.builder()
                                        .fieldMember(fieldMember)
                                        .substituteMember(substituteMember)
                                        .afterMatching(afterMatching)
                                        .teamId(member.getOpenTeam().getId())
                                        .build();
                                scorerRepository.save(scorerMember);
                            } else {
                                Scorer scorerMember = Scorer.builder()
                                        .fieldMember(null)
                                        .substituteMember(null)
                                        .afterMatching(afterMatching)
                                        .teamId(beforeMatching.getApply().getTeam().getId())
                                        .build();
                                scorerRepository.save(scorerMember);
                            }
                        }
                    } else throw new IllegalArgumentException("득점과 득점자 수가 일치하지 않습니다.");
                } else if (member.getOpenTeam().getId().equals(beforeMatching.getApply().getApplyTeam().getId())) {
                    if (afterMatching.getOpponentScore() == matchResultRequest.getScorers().size()) {
                        for (MatchMemberRequest scorer : matchResultRequest.getScorers()) {
                            if (!scorer.getAnonymous()) {
                                FieldMember fieldMember = fieldMemberRepository.findByMemberIdAndTeamId(scorer.getMemberId(), member.getOpenTeam().getId()).orElse(null);
                                SubstituteMember substituteMember = searchSubMember(substituteMembers, scorer.getMemberId());
                                if (fieldMember == null && substituteMember == null) throw new IllegalArgumentException("해당 선수는 팀에 소속되어 있지 않습니다.");
                                Scorer scorerMember = Scorer.builder()
                                        .fieldMember(fieldMember)
                                        .substituteMember(substituteMember)
                                        .afterMatching(afterMatching)
                                        .teamId(member.getId())
                                        .build();
                                scorerRepository.save(scorerMember);
                            } else {
                                Scorer scorerMember = Scorer.builder()
                                        .fieldMember(null)
                                        .substituteMember(null)
                                        .afterMatching(afterMatching)
                                        .teamId(beforeMatching.getApply().getApplyTeam().getId())
                                        .build();
                                scorerRepository.save(scorerMember);
                            }
                        }
                    } else throw new IllegalArgumentException("득점과 득점자 수가 일치하지 않습니다.");
                }
                if (matchResultRequest.getMvpPlayer() != null) {
                    Member mvpPlayer = participationRepository.findByMemberIdAndTeamIdTrue(matchResultRequest.getMvpPlayer(), member.getOpenTeam().getId()).orElseThrow(
                            () -> new IllegalArgumentException("해당 팀에 속해 있는 선수가 아닙니다.")).getMember();
                    afterMatching.editMVPNickname(mvpPlayer.getNickname());
                    mvpPlayer.getAbility().updateMVPPoint();
                }
                if (matchResultRequest.getMoodMaker() != null) {
                    Member moodMaker = participationRepository.findByMemberIdAndTeamIdTrue(matchResultRequest.getMoodMaker(), member.getOpenTeam().getId()).orElseThrow(
                            () -> new IllegalArgumentException("해당 팀에 속해 있는 선수가 아닙니다.")).getMember();
                    afterMatching.editMoodMaker(moodMaker.getNickname());
                    moodMaker.getAbility().updateCharmingPoint();
                }
                distributePoint(member.getOpenTeam().getId(), afterMatching.getId());
                History history = historyRepository.findByBeforeMatchingIdAndAfterMatchingId(beforeMatching.getId(), afterMatching.getId()).orElse(null);
                if (history == null) {
                    History saveHistory = History.builder()
                            .beforeMatching(afterMatching.getBeforeMatching())
                            .afterMatching(afterMatching)
                            .build();
                    historyRepository.save(saveHistory);
                }
            } else throw new IllegalArgumentException("해당 팀의 주장이 아닙니다.");
        } else throw new IllegalArgumentException("상대 팀이 결과를 인정하지 않았습니다.");
    }

    @Transactional
    void distributePoint(Long teamId, Long afterMatchingId) {
        List<String> results = Arrays.asList("승리", "무승부", "패배");
        AfterMatching afterMatching = afterMatchingRepository.findById(afterMatchingId).orElseThrow(() -> new IllegalArgumentException("성사된 대결이 존재하지 않습니다."));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("해당 팀을 찾을 수 없습니다."));
        team.getRecord().updateTotalGameCount();
        List<FieldMember> fieldMembers = fieldMemberRepository.findAllByMatchFieldMembersAndAnonymousFalse(team.getId(), afterMatching.getBeforeMatching().getId());

        if (team.getName().equals(afterMatching.getBeforeMatching().getTeamName())) {
            if (afterMatching.getScore() > afterMatching.getOpponentScore()) {
                afterMatching.editResult(results.get(0), results.get(2));
                for (FieldMember fieldMember : fieldMembers) {
                    distributePositionPoint(fieldMember);
                }
                List<SubstituteMember> substituteMembers = substituteRepository.findAllByMatchSubstituteMembersAndAnonymousFalse(team.getId(), afterMatching.getId());
                for (SubstituteMember substituteMember : substituteMembers) {
                    distributePositionPoint(substituteMember);
                }
                team.getRecord().updateWinCount();
                team.getRecord().updateWinRate(((double) (team.getRecord().getWinCount() / team.getRecord().getTotalGameCount())) * 100.0);
            } else if (afterMatching.getScore() < afterMatching.getOpponentScore()) {
                afterMatching.editResult(results.get(2), results.get(0));
                for (FieldMember fieldMember : fieldMembers) {
                    distributePositionPoint(fieldMember);
                }
                List<SubstituteMember> substituteMembers = substituteRepository.findAllByMatchSubstituteMembersAndAnonymousFalse(team.getId(), afterMatching.getId());
                for (SubstituteMember substituteMember : substituteMembers) {
                    distributePositionPoint(substituteMember);
                }
                team.getRecord().updateLoseCount();
                team.getRecord().updateWinRate(((double) ((team.getRecord().getWinCount() / team.getRecord().getTotalGameCount()))) * 100.0);
            } else {
                afterMatching.editResult(results.get(1), results.get(1));
                team.getRecord().updateDrawCount();
                team.getRecord().updateWinRate(((double) ((team.getRecord().getWinCount() / team.getRecord().getTotalGameCount()))) * 100.0);
            }
        } else if (team.getName().equals(afterMatching.getBeforeMatching().getOpposingTeamName())) {
            if (afterMatching.getScore() > afterMatching.getOpponentScore()) {
                afterMatching.editResult(results.get(0), results.get(2));
                team.getRecord().updateLoseCount();
                team.getRecord().updateWinRate(((double) ((team.getRecord().getWinCount() / team.getRecord().getTotalGameCount()))) * 100.0);
            } else if (afterMatching.getScore() < afterMatching.getOpponentScore()) {
                afterMatching.editResult(results.get(2), results.get(0));
                for (FieldMember fieldMember : fieldMembers) {
                    distributePositionPoint(fieldMember);
                }
                List<SubstituteMember> substituteMembers = substituteRepository.findAllByMatchSubstituteMembersAndAnonymousFalse(team.getId(), afterMatching.getId());
                for (SubstituteMember substituteMember : substituteMembers) {
                    distributePositionPoint(substituteMember);
                }
                team.getRecord().updateWinCount();
                team.getRecord().updateWinRate(((double) ((team.getRecord().getWinCount() / team.getRecord().getTotalGameCount()))) * 100.0);
            } else {
                afterMatching.editResult(results.get(1), results.get(1));
                team.getRecord().updateDrawCount();
                team.getRecord().updateWinRate(((double) ((team.getRecord().getWinCount() / team.getRecord().getTotalGameCount()))) * 100.0);
            }
        }
    }

    @Transactional
    void distributePositionPoint(FieldMember fieldMember) {
        switch (fieldMember.getPosition()) {
            case "striker":
                fieldMember.getMember().getAbility().updateStrikePoint();
                break;
            case "midfielder":
                fieldMember.getMember().getAbility().updateMidfielderPoint();
                break;
            case "defender":
                fieldMember.getMember().getAbility().updateDefenderPoint();
                break;
            case "goalkeeper":
                fieldMember.getMember().getAbility().updateGoalkeeperPoint();
                break;
        }
    }
    @Transactional
    void distributePositionPoint(SubstituteMember substituteMember) {
        switch (substituteMember.getPosition()) {
            case "striker":
                substituteMember.getMember().getAbility().updateStrikePoint();
                break;
            case "midfielder":
                substituteMember.getMember().getAbility().updateMidfielderPoint();
                break;
            case "defender":
                substituteMember.getMember().getAbility().updateDefenderPoint();
                break;
            case "goalkeeper":
                substituteMember.getMember().getAbility().updateGoalkeeperPoint();
                break;
        }
    }

    private SubstituteMember searchSubMember(List<SubstituteMember> substituteMembers, Long memberId) {
        for (SubstituteMember substituteMember : substituteMembers) {
            if (!substituteMember.getAnonymous()) {
                if (substituteMember.getMember().getId().equals(memberId)) {
                    return substituteMember;
                } break;
            }
        }
        return null;
    }
}
