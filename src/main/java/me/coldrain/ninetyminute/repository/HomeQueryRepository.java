package me.coldrain.ninetyminute.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.entity.Home;
import me.coldrain.ninetyminute.entity.QHome;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.coldrain.ninetyminute.entity.QHome.home;

@Repository
@RequiredArgsConstructor
public class HomeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Home> search() {
        return queryFactory.select(home)
                .from(home)
                .fetch();
    }

    public List<List<String>> searchWeekdays() {
        return queryFactory.select(home.weekdays)
                .from(home)
                .fetch();
    }

    public List<List<String>> searchWeekdaysV2() {
        return queryFactory.select(home.weekdays)
                .from(home).innerJoin(home.weekdays)
                .fetch();
    }
}
