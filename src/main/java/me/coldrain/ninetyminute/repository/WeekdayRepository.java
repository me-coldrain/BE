package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Weekday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeekdayRepository extends JpaRepository<Weekday, Long> {
    @Query("SELECT w FROM Weekday w WHERE w.team.id = :teamId")
    List<Weekday> findAllByTeamId(final Long teamId);
}
