package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import me.coldrain.ninetyminute.dto.request.TeamRegisterRequest;
import me.coldrain.ninetyminute.entity.*;
import me.coldrain.ninetyminute.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamQueryRepository teamQueryRepository;
    private final WeekdayRepository weekdayRepository;
    private final TimeRepository timeRepository;
    private final RecordRepository recordRepository;

    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public void registerTeam(final TeamRegisterRequest request, final Long memberId) {
        //final Map<String, String> uploadFile = awsS3Service.uploadFile(request.getTeamImageFile());
        //final String imageFileUrl = uploadFile.get("url"); // -> 버켓 생성 후 주석 풀기

        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        final Record emptyRecord = recordRepository.save(new Record());
        final Team team = Team.builder()
                .name(request.getTeamName())
//                .teamProfileUrl(imageFileUrl) // -> 버켓 생성 후 주석 풀기
                .introduce(request.getIntroduce())
                .mainArea(request.getMainArea())
                .preferredArea(request.getPreferredArea())
                .point(0)
                .record(emptyRecord)
                .build();

        teamRepository.save(team);
        member.setOpenTeam(team);

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
