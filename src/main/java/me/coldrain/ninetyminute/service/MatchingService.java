package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import me.coldrain.ninetyminute.dto.request.ApprovedMatchRequest;
import me.coldrain.ninetyminute.dto.response.OfferMatchResponse;
import me.coldrain.ninetyminute.entity.Apply;
=======
import me.coldrain.ninetyminute.dto.response.ApprovedMatchResponse;
>>>>>>> bb511c2 (Feat: "경기 성사 목록 조회 API")
import me.coldrain.ninetyminute.entity.BeforeMatching;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.repository.ApplyRepository;
import me.coldrain.ninetyminute.repository.BeforeMatchingRepository;
<<<<<<< HEAD
import me.coldrain.ninetyminute.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingService {

    private final TeamRepository teamRepository;
    private final ApplyRepository applyRepository;
    private final BeforeMatchingRepository beforeMatchingRepository;

    @Transactional
    public String approvedMatch(Long apply_team_id, ApprovedMatchRequest approvedMatchRequest, Member member) {
        if (!apply_team_id.equals(member.getOpenTeam().getId())) {
            String teamName = teamRepository.findById(member.getOpenTeam().getId()).orElseThrow(
                    () -> new IllegalArgumentException("팀을 찾을 수 없습니다.")).getName();

            String opposingTeamName = teamRepository.findById(apply_team_id).orElseThrow(
                    () -> new IllegalArgumentException("팀을 찾을 수 없습니다.")).getName();

            Apply applyMatch = applyRepository.findByApplyTeamIdAndTeamId(apply_team_id, member.getOpenTeam().getId()).orElseThrow(
                    () -> new IllegalArgumentException("신청한 팀을 찾을 수 없습니다.")
            );

            applyMatch.changeApproved(true);
            applyRepository.save(applyMatch);

            BeforeMatching beforeMatching = BeforeMatching.builder()
                    .apply(applyMatch)
                    .matchDate(approvedMatchRequest.getMatchDate())
                    .location(approvedMatchRequest.getMatchLocation())
                    .opposingTeamName(opposingTeamName)
                    .teamName(teamName)
                    .build();

            beforeMatchingRepository.save(beforeMatching);
            return "완료됐습니다.";
        } else throw new IllegalArgumentException("자신의 팀에 대결 신청은 불가능합니다.");
    }

    @Transactional
    public void rejectApplyMatch(Long applyTeamId, Long teamId) {
        Apply applyMatch = applyRepository.findByApplyTeamIdAndTeamId(applyTeamId, teamId).orElseThrow(
                () -> new IllegalArgumentException("신청한 대결이 존재하지 않습니다.")
        );
        applyRepository.delete(applyMatch);
    }


    public List<OfferMatchResponse> searchOfferMatches(Long teamId, Member member) {
        List<OfferMatchResponse> offerMatchResponseList = new ArrayList<>();
        if (member.getOpenTeam().getId().equals(teamId)) {
            List<Apply> applyList = applyRepository.findAllByTeamIdOrderByCreatedDate(teamId);
            // 해당 팀에 신청된 경기가 없어도 null이 전달되야한다.
            if (!applyList.isEmpty()) {
                for (Apply apply : applyList) {
                    OfferMatchResponse offerMatchResponse = OfferMatchResponse.builder()
                            .opposingTeamId(apply.getApplyTeam().getId())
                            .opposingTeamName(apply.getApplyTeam().getName())
                            .opposingTeamPoint(apply.getApplyTeam().getPoint())
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
=======
import me.coldrain.ninetyminute.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Transactional
@Service
public class MatchingService {

    final MemberRepository memberRepository;
    final ApplyRepository applyRepository;
    final BeforeMatchingRepository beforeMatchingRepository;


    @Transactional(readOnly = true)
    public List<ApprovedMatchResponse> searchApprovedMatch(Long team_id, Member member) {

        List<BeforeMatching> beforeMatchingList = beforeMatchingRepository.findAllByBeforeMatching(team_id);    // apply 의 approved == true 일 때만
        List<ApprovedMatchResponse> approvedMatchResponseList = new ArrayList<>();

        for (BeforeMatching beforeMatching : beforeMatchingList) {
            Member captainMember = memberRepository.findByOpenTeamID(beforeMatching.getApply().getApplyTeam().getId()).orElseThrow(() -> new IllegalAccessError(
                    "해당 팀을 찾을 수 없습니다."));
            Long applyTeamId = beforeMatching.getApply().getApplyTeam().getId();
            LocalDateTime from = LocalDateTime.now();
            LocalDateTime to = LocalDateTime.ofInstant(beforeMatching.getMatchDate().toInstant(), ZoneId.systemDefault());
            ApprovedMatchResponse approvedMatchResponse = ApprovedMatchResponse.builder()
                    .matchId(beforeMatching.getApply().getId())
                    .opposingTeamId(beforeMatching.getApply().getApplyTeam().getId())
                    .opposingTeamName(beforeMatching.getOpposingTeamName())
                    .contact(captainMember.getContact())
                    .phone(captainMember.getPhone())
                    .createdAtMatch(java.sql.Timestamp.valueOf(applyRepository.findByApplyTeamIdAndTeamId(applyTeamId, team_id).orElseThrow(() ->
                            new IllegalAccessError("해당 팀을 찾을 수 없습니다.")).getCreatedDate()))
                    .matchDate(beforeMatching.getMatchDate())
                    .dDay(ChronoUnit.DAYS.between(from, to))
                    .matchLocation(beforeMatching.getLocation())
                    .createdDate(beforeMatching.getCreatedDate())
                    .modifiedDate(beforeMatching.getModifiedDate())
                    .build();
            approvedMatchResponseList.add(approvedMatchResponse);
        }
        return approvedMatchResponseList;
>>>>>>> bb511c2 (Feat: "경기 성사 목록 조회 API")
    }
}
