package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findFirst10ByOrderByWinPointDesc();

    List<Record> findAllByOrderByWinPointDesc();

}
