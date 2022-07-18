package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Scorer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScorerRepository extends JpaRepository<Scorer, Long> {
}
