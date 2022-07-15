package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
