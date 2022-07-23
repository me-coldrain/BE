package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Scorer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScorerRepository extends JpaRepository<Scorer, Long> {

    @Query("select s from Scorer s where s.afterMatching.id = :afterMatchingId and s.teamId = :teamId")
    List<Scorer> findAllByAfterMatchingIdAndTeamId(Long afterMatchingId, Long teamId);

    @Query("select s from Scorer s where s.afterMatching.id = :afterMatchingId and s.teamId = :applyTeamId")
    List<Scorer> findAllByAfterMatchingIdAndApplyTeamId(Long afterMatchingId, Long applyTeamId);
}
