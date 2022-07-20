package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("select h from History h where h.afterMatching.beforeMatching.apply.team.id = :teamId order by h.createdDate desc")
    List<History> findAllByHomeTeamId(final Long teamId);

    @Query("select h from History h where h.afterMatching.beforeMatching.apply.applyTeam.id = :teamId order by h.createdDate desc")
    List<History> findAllByAwayTeamId(final Long teamId);

    @Query("select h from History h where h.beforeMatching.id = :RecentBeforeMatchingId")
    Optional<History> findByRecentHistory(Long RecentBeforeMatchingId);

    @Query("select h from History h where h.beforeMatching.id = :beforeMatchingId and h.afterMatching.id = :afterMatchingId")
    Optional<History> findByBeforeMatchingIdAndAfterMatchingId(final Long beforeMatchingId, final Long afterMatchingId);
}
