package me.coldrain.ninetyminute.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.QTeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.coldrain.ninetyminute.entity.QParticipation.participation;
import static me.coldrain.ninetyminute.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class TeamQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<TeamListSearch> findAllTeamListSearch(
            final TeamListSearchCondition searchCondition,
            final Pageable pageable) {

        // TODO: 2022-07-07 모집 중 팀만 조회되도록 변경해야 함.
        final List<TeamListSearch> content = queryFactory.select(new QTeamListSearch(
                        team.id,
                        team.name,
                        JPAExpressions.select(participation.count())
                                .from(participation)
                                .where(participation.approved.eq(true)
                                        , participation.team.id.eq(team.id)),
                        team.mainArea))
                .from(team)
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
