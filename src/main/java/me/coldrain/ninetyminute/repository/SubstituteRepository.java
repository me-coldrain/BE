package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.FieldMember;
import me.coldrain.ninetyminute.entity.SubstituteMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubstituteRepository extends JpaRepository<SubstituteMember, Long> {

    @Query("select sm from SubstituteMember sm where sm.member.id = :memberId and sm.team.id = :teamId")
    Optional<SubstituteMember> findByMemberIdAndTeamId(Long memberId, Long teamId);

    @Query("select sm from SubstituteMember sm where sm.team.id = :teamId and sm.afterMatching.id = :afterMatchId and sm.anonymous = false ")
    List<SubstituteMember> findAllByMatchSubstituteMembers(Long teamId, Long afterMatchId);

    @Query("select sm from SubstituteMember sm where sm.team.id = :teamId and sm.afterMatching.id = :afterMatchId")
    List<SubstituteMember> findAllByAllMatchSubstituteMembers(Long teamId, Long afterMatchId);

    @Query("select sm from SubstituteMember sm where sm.member.id = :MemberId")
    List<SubstituteMember> findAllByGameSubstituteMembers(Long MemberId);
}
