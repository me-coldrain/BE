package me.coldrain.ninetyminute.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.QTeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import me.coldrain.ninetyminute.entity.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static me.coldrain.ninetyminute.entity.QParticipation.participation;
import static me.coldrain.ninetyminute.entity.QRecord.record;
import static me.coldrain.ninetyminute.entity.QTeam.team;
import static me.coldrain.ninetyminute.entity.QTime.time1;
import static me.coldrain.ninetyminute.entity.QWeekday.weekday1;

@Repository
@RequiredArgsConstructor
public class TeamQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final WeekdayRepository weekdayRepository;
    private final TimeRepository timeRepository;

    public Slice<TeamListSearch> findAllTeamListSearch(
            final TeamListSearchCondition searchCondition,
            final Pageable pageable) {

        // TODO: 2022-07-09 검색 필터에 요일, 시간, 승률도 추가해야 함.
        final List<TeamListSearch> content = queryFactory.select(
                new QTeamListSearch(
                        team.id,
                        team.name,
                        JPAExpressions.select(participation.count().add(1)) // 주장 +1
                                .from(participation)
                                .where(participation.approved.eq(true)
                                        , participation.team.id.eq(team.id)),
                        team.mainArea,
                        team.preferredArea,
                        team.record.winRate,
                        team.recruit,
                        team.match,
                        team.record.totalGameCount,
                        team.record.winCount,
                        team.record.drawCount,
                        team.record.loseCount,
                        team.createdDate,
                        team.modifiedDate))
                .from(team)
                .innerJoin(team.record, record)
                .where(containsIgnoreCaseTeamName(searchCondition.getTeamName()),   // 팀 이름
                        containsAddress(searchCondition.getAddress()),  // 주소
                        eqMatch(searchCondition.getMatch()),    // 대결 등록 상태
                        eqRecruit(searchCondition.getRecruit()) // 모집 상태
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(team.createdDate.desc())
                .fetch();

        for (TeamListSearch c : content) {
            final List<String> weekdays = weekdayRepository.findAllByTeamId(c.getTeamId())
                    .stream()
                    .map(Weekday::getWeekday)
                    .collect(toList());

            c.setWeekdays(weekdays);

            final List<String> timeList = timeRepository.findAllByTeamId(c.getTeamId())
                    .stream()
                    .map(Time::getTime)
                    .collect(toList());

            c.setTime(timeList);
        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression eqMatch(Boolean match) {
        return match != null ? team.match.eq(match) : null;
    }

    private BooleanExpression eqRecruit(Boolean recruit) {
        return recruit != null ? team.recruit.eq(recruit) : null;
    }

    private BooleanExpression containsIgnoreCaseTeamName(String teamName) {
        return teamName != null ? team.name.containsIgnoreCase(teamName) : null;
    }

    private BooleanExpression containsAddress(String address) {
        return address != null ? team.mainArea.contains(address) : null;
    }

}
