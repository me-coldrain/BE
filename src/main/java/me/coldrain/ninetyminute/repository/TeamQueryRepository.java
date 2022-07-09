package me.coldrain.ninetyminute.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.QTeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import me.coldrain.ninetyminute.entity.QRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.coldrain.ninetyminute.entity.QParticipation.participation;
import static me.coldrain.ninetyminute.entity.QRecord.record;
import static me.coldrain.ninetyminute.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class TeamQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<TeamListSearch> findAllTeamListSearch(
            final TeamListSearchCondition searchCondition,
            final Pageable pageable) {

        // TODO: 2022-07-09 검색 피렅에 요일, 시간, 승률, 모집상태, 대결 등록 상태도 추가해야 함.
        final List<TeamListSearch> content = queryFactory.select(new QTeamListSearch(
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
                .where(containsIgnoreCaseTeamName(searchCondition.getTeamName()),
                        containsAddress(searchCondition.getAddress())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(team.createdDate.desc())
                .fetch();

        // TODO: 2022-07-07 6:42 Total 구하는 쿼리도 구해야 한다.
        return new PageImpl<>(content, pageable, 0);
    }

    private BooleanExpression containsIgnoreCaseTeamName(String teamName) {
        return teamName != null ? team.name.containsIgnoreCase(teamName) : null;
    }

    private BooleanExpression containsAddress(String address) {
        return address != null ? team.mainArea.contains(address) : null;
    }
}
