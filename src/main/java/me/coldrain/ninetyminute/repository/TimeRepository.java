package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Long> {

}
