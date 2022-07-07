package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.request.TeamParticipateRequest;
import me.coldrain.ninetyminute.entity.Participation;
import me.coldrain.ninetyminute.entity.Team;
import me.coldrain.ninetyminute.repository.ParticipationRepository;
import me.coldrain.ninetyminute.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void participate(final Long teamId, final TeamParticipateRequest request) {
        final Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("참여하려는 팀이 존재하지 않습니다."));

        // TODO: 2022-07-07 로그인한 사용자를 주입 하도록 코드 변경 필요
        final Participation participation = Participation.builder()
                .member(null)
                .team(team)
                .approved(false)
                .answer(request.getAnswer())
                .build();

        participationRepository.save(participation);
    }

    @Transactional
    public void approve(final Long teamId, final Long memberId) {
        final Participation participation = participationRepository.findByTeamIdAndMemberId(teamId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("참여 이력이 존재하지 않습니다."));

        participation.changeApproved(true);
    }

    @Transactional
    public void disapprove(final Long teamId, final Long memberId) {
        final Participation participation = participationRepository.findByTeamIdAndMemberId(teamId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("참여 이력이 존재하지 않습니다."));

        participationRepository.delete(participation);
    }
}
