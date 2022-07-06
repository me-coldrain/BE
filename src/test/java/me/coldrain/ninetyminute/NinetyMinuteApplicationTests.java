package me.coldrain.ninetyminute;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.coldrain.ninetyminute.entity.QHello;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class NinetyMinuteApplicationTests {

    @Test
    void contextLoads() {

    }

}
