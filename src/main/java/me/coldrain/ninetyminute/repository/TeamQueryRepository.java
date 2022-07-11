package me.coldrain.ninetyminute.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.QTeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import me.coldrain.ninetyminute.entity.Time;
import me.coldrain.ninetyminute.entity.Weekday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static me.coldrain.ninetyminute.entity.QParticipation.participation;
import static me.coldrain.ninetyminute.entity.QRecord.record;
import static me.coldrain.ninetyminute.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class TeamQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final WeekdayRepository weekdayRepository;
    private final TimeRepository timeRepository;

    public Page<TeamListSearch> findAllTeamListSearch(
            final TeamListSearchCondition searchCondition,
            final Pageable pageable) {

        // TODO: 2022-07-09 검색 필터에 승률도 추가해야 함.
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

        final JPAQuery<Long> countQuery = queryFactory.select(team.count())
                .from(team)
                .where(containsIgnoreCaseTeamName(searchCondition.getTeamName()),   // 팀 이름
                        containsAddress(searchCondition.getAddress()),  // 주소
                        eqMatch(searchCondition.getMatch()),    // 대결 등록 상태
                        eqRecruit(searchCondition.getRecruit()) // 모집 상태
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (TeamListSearch c : content) {
            final List<String> weekdays = this.findWeekdaysByTeamId(c.getTeamId());
            c.setWeekdays(weekdays);

            if (searchCondition.getWeekdays() != null) {
                final boolean weekdaysContains = weekdays.stream()
                        .anyMatch(wd -> searchCondition.getWeekdays().contains(wd));
                if (!weekdaysContains) {
                    content.remove(c);
                }
            }

            final List<String> timeList = this.findTimeAllByTeamId(c.getTeamId());
            c.setTime(timeList);

            if (searchCondition.getTime() != null) {
                final boolean timeContains = timeList.stream()
                        .anyMatch(t -> searchCondition.getTime().contains(t));

                if (!timeContains) {
                    content.remove(c);
                }
            }
        }

        // count 쿼리 생략
        // 1. 페이지 시작이면서 컨텐츠 사이즈가 페이지 사이즈보다 작을 때
        // 2. 마지막 페이지 일 때 (offset + 컨텐츠 사이즈를 더해서 전체 사이즈를 구한다.)
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
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

    private List<String> findWeekdaysByTeamId(Long teamId) {
        return weekdayRepository.findAllByTeamId(teamId)
                .stream()
                .map(Weekday::getWeekday)
                .collect(toList());
    }

    private List<String> findTimeAllByTeamId(Long teamId) {
        return timeRepository.findAllByTeamId(teamId)
                .stream()
                .map(Time::getTime)
                .collect(toList());
    }

}
