package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import me.coldrain.ninetyminute.dto.request.TeamRegisterRequest;
import me.coldrain.ninetyminute.entity.Record;
import me.coldrain.ninetyminute.entity.Team;
import me.coldrain.ninetyminute.entity.Time;
import me.coldrain.ninetyminute.entity.Weekday;
import me.coldrain.ninetyminute.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamQueryRepository teamQueryRepository;
    private final WeekdayRepository weekdayRepository;
    private final TimeRepository timeRepository;
    private final RecordRepository recordRepository;

    @Transactional
    public void registerTeam(final TeamRegisterRequest request) {
        final Record emptyRecord = recordRepository.save(new Record());
        final Team team = Team.builder()
                .name(request.getTeamName())
                .teamProfileUrl(null) // 추후 S3 링크로 변경
                .introduce(request.getIntroduce())
                .mainArea(request.getMainArea())
                .preferredArea(request.getPreferredArea())
                .question(request.getQuestion())
                .point(0)
                .record(emptyRecord)
                .build();

        teamRepository.save(team);

        request.getWeekday()
                .forEach(weekday -> weekdayRepository.save(new Weekday(weekday, team)));

        request.getTime()
                .forEach(time -> timeRepository.save(new Time(time, team)));
    }

    public Page<TeamListSearch> searchTeamList(final TeamListSearchCondition searchCondition, final Pageable pageable) {
        return teamQueryRepository.findAllTeamListSearch(searchCondition, pageable);
    }

    public String findQuestionByTeamId(final Long teamId) {
        return teamRepository.findQuestionByTeamId(teamId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 팀 질문이 존재하지 않습니다."));
    }
}
