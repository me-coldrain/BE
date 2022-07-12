package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Weekday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface WeekdayRepository extends JpaRepository<Weekday, Long> {
    @Query("SELECT wd FROM Weekday wd WHERE wd.team.id = :teamId")
    List<Weekday> findAllByTeamId(Long teamId);
}