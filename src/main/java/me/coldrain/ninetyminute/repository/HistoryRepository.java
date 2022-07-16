package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("select h from History h where h.afterMatching.beforeMatching.apply.team.id =: teamId order by h.createdDate desc")
    List<History> findAllByHomeTeamId(Long teamId);

    @Query("select h from History h where h.afterMatching.beforeMatching.apply.applyTeam.id =: teamId order by h.createdDate desc")
    List<History> findAllByAwayTeamId(Long teamId);
}
