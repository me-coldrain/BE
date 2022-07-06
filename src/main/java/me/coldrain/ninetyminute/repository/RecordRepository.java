package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {

}
