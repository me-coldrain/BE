package me.coldrain.ninetyminute.repository;


import me.coldrain.ninetyminute.entity.BeforeMatching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BeforeMatchingRepository extends JpaRepository<BeforeMatching, Long> {

    @Query("select bm from BeforeMatching bm where bm.apply.team.id = :teamId and bm.apply.approved = true order by bm.createdDate desc")
    List<BeforeMatching> findAllByBeforeMatching(final Long teamId);

    @Query("select bm from BeforeMatching bm where bm.apply.team.id = :teamId and bm.apply.approved = true order by bm.createdDate desc")
    Optional<BeforeMatching> findByRecentBeforeMatching(Long teamId);

    @Query("select bm from BeforeMatching bm where bm.apply.id = :applyId and bm.apply.approved = true")
    Optional<BeforeMatching> findByApplyIdApprovedTrue(final Long applyId);
}
