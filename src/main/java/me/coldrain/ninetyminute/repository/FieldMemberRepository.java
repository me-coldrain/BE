package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.FieldMember;
import me.coldrain.ninetyminute.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FieldMemberRepository extends JpaRepository<FieldMember, Long> {

    @Query("select fm from FieldMember fm where fm.member.id = :memberId and fm.team.id = :teamId")
    Optional<FieldMember> findByMemberIdAndTeamId(Long memberId, Long teamId);

    @Query("select fm from FieldMember fm where fm.team.id = :teamId and fm.afterMatching.beforeMatching.id = :beforeMatchId and fm.afterMatching.admitStatus = true and fm.anonymous = false ")
    List<FieldMember> findAllByMatchFieldMembersAndAnonymousFalse(Long teamId, Long beforeMatchId);

    @Query("select fm from FieldMember fm where fm.team.id = :teamId and fm.afterMatching.beforeMatching.id = :beforeMatchId and fm.afterMatching.admitStatus = true")
    List<FieldMember> findAllByMatchFieldMembers(Long teamId, Long beforeMatchId);

    @Query("select fm from FieldMember fm where fm.afterMatching.id = :afterMatchingId")
    List<FieldMember> findAllByAfterMatchingId(Long afterMatchingId);

}
