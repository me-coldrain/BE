package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    @Query("SELECT a FROM Apply a WHERE a.applyTeam.id = :applyTeamId AND a.team.id = :teamId AND a.applyTeam.deleted = false AND a.team.deleted = false")
    Optional<Apply> findByApplyTeamIdAndTeamId(final Long applyTeamId, final Long teamId);

    @Query("SELECT a FROM Apply a WHERE a.applyTeam.id = :applyTeamId AND a.team.id = :teamId and a.endMatchStatus = true and a.opposingTeamEndMatchStatus = true")
    Optional<Apply> findByApplyTeamIdAndTeamIdAndEndMatch(final Long applyTeamId, final Long teamId);

    @Query("SELECT a FROM Apply a WHERE a.applyTeam.id = :applyTeamId AND a.team.id = :teamId AND a.approved = true AND a.team.deleted = false and a.applyTeam.deleted = false")
    Optional<Apply> findByApplyTeamIdAndTeamIdTrue(final Long applyTeamId, final Long teamId);

    @Query("select a from Apply a where a.team.id = :teamId AND a.approved = true  AND a.endMatchStatus = true  AND  a.opposingTeamEndMatchStatus = true")
    List<Apply> findAllByApplyEndTeamId(final Long teamId);

    @Query("select a from Apply a where a.applyTeam.id = :ApplyTeamId AND a.approved = true  AND a.endMatchStatus = true  AND  a.opposingTeamEndMatchStatus = true")
    List<Apply> findAllByApplyEndApplyTeamId(final Long ApplyTeamId);

    @Query("select a from Apply a where a.team.id = :teamId order by a.createdDate desc ")
    List<Apply> findAllByTeamIdOrderByCreatedDate(final Long teamId);

    @Query("select a from Apply a where a.applyTeam.id = :teamId or a.team.id = :teamId and a.team.deleted = false and a.applyTeam.deleted = false and a.approved = true and a.endMatchStatus = false and a.opposingTeamEndMatchStatus = false order by a.modifiedDate desc ")
    List<Apply> findAllByApprovedMatches(final Long teamId);
}
