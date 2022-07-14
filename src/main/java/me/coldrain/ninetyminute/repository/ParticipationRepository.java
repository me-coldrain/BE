package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    @Query("SELECT p FROM Participation p WHERE p.team.id = :teamId AND p.member.id = :memberId")
    Optional<Participation> findByTeamIdAndMemberId(final Long teamId, final Long memberId);

    @Query("SELECT p FROM Participation p WHERE p.member.id = :memberId AND p.approved = true")
    List<Participation> findAllByMemberIdTrue(final Long memberId);

    @Query("SELECT p FROM Participation p WHERE p.member.id = :memberId AND p.approved = false")
    List<Participation> findAllByMemberIdFalse(final Long memberId);

    @Query("SELECT p FROM Participation p WHERE p.member.id = :memberId AND p.team.id = :teamId AND p.approved = true")
    Participation findByTeamIdAndMemberIdTrue(final Long memberId, final Long teamId);

    @Query("SELECT p FROM Participation p WHERE p.member.id = :memberId AND p.team.id = :teamId AND p.approved = false")
    Participation findByTeamIdAndMemberIdFalse(final Long memberId, final Long teamId);

    @Query("SELECT p FROM Participation p WHERE p.team.id = :teamId AND p.approved = true")
    List<Participation> findAllByTeamIdTrue(final Long teamId);

    @Query("SELECT p FROM Participation p WHERE p.team.id = :teamId AND p.approved = false")
    List<Participation> findAllByTeamIdFalse(final Long teamId);
}
