package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByOrderByWinPointDesc();

    List<Record> findAllByOrderByWinPointDescWinRateDesc();
}
