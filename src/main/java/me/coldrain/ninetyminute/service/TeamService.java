package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import me.coldrain.ninetyminute.dto.request.RecruitStartRequest;
import me.coldrain.ninetyminute.dto.request.TeamRegisterRequest;
import me.coldrain.ninetyminute.entity.*;
import me.coldrain.ninetyminute.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        final Team openTeam = member.getOpenTeam();
        if (openTeam != null) {
            throw new IllegalArgumentException("이미 개설한 팀이 존재 합니다.");
        }

        final Record emptyRecord = recordRepository.save(new Record());
        final Team team = Team.builder()
                .name(request.getTeamName())
//                .teamProfileUrl(imageFileUrl) // -> 버켓 생성 후 주석 풀기
                .introduce(request.getIntroduce())
                .mainArea(request.getMainArea())
                .preferredArea(request.getPreferredArea())
                .point(0)
                .recruit(false)
                .match(false)
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

    public Team findById(final Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));
    }

    @Transactional
    public void startRecruit(final Long teamId, final Member member, final RecruitStartRequest request) {
        final Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

        final Long openTeamId = member.getOpenTeam().getId();
        if (!openTeamId.equals(teamId)) {
            throw new IllegalArgumentException("팀 개설자만 팀원 모집을 할 수 있습니다.");
        }

        final Boolean recruit = team.getRecruit();
        if (recruit.equals(true)) {
            throw new IllegalArgumentException("이미 팀원 모집 중입니다.");
        }

        team.changeRecruit(true);
        team.setQuestion(request.getQuestion());
    }

    @Transactional
    public void endRecruit(final Long teamId, final Member member) {
        final Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

        final Long openTeamId = member.getOpenTeam().getId();
        if (!openTeamId.equals(teamId)) {
            throw new IllegalArgumentException("팀 개설자만 팀원 모집 종료를 할 수 있습니다.");
        }

        final Boolean recruit = team.getRecruit();
        if (recruit.equals(false)) {
            throw new IllegalArgumentException("이미 팀원 모집을 종료 상태입니다.");
        }

        team.changeRecruit(false);
        team.setQuestion(null);
    }

    @Transactional
    public void registMatch(final Long teamId, final Member member) {
        final Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

        final Long openTeamId = member.getOpenTeam().getId();
        if (!openTeamId.equals(team.getId())) {
            throw new IllegalArgumentException("팀 개설자가 아닙니다.");
        }

        if (team.getMatch().equals(true)) {
            throw new IllegalArgumentException("이미 매칭 등록 상태입니다.");
        }

        team.changeMatch(true);
    }
}
