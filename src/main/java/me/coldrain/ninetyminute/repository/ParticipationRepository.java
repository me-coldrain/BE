package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    @Query("SELECT p FROM Participation p WHERE p.team.id = :teamId AND p.member.id = :memberId")
    Optional<Participation> findByTeamIdAndMemberId(final Long teamId, final Long memberId);
}
