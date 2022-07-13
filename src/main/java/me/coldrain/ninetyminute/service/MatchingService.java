package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.request.ApprovedMatchRequest;
import me.coldrain.ninetyminute.entity.Apply;
import me.coldrain.ninetyminute.entity.BeforeMatching;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.repository.ApplyRepository;
import me.coldrain.ninetyminute.repository.BeforeMatchingRepository;
import me.coldrain.ninetyminute.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
