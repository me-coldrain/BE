package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByOrderByWinPointDesc();

    @Query("select r from Record r inner join Team t on r.id = t.record.id where t.deleted = false order by r.winPoint desc, r.winRate desc")
    List<Record> findAllByOrderByWinPointDescWinRateDesc();
}
