package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("SELECT R.id FROM Record R")
    List<Long> findFirst10ByOrderByWinPointDesc();

    @Query("SELECT R.id FROM Record R")
    List<Long> findAllByOrderByWinPointDesc();

}
