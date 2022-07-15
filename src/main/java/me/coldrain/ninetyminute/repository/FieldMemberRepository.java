package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.FieldMember;
import me.coldrain.ninetyminute.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FieldMemberRepository extends JpaRepository<FieldMember, Long> {

    @Query("select fm from FieldMember fm where fm.id =: fieldMemberId and fm.team.id =: teamId")
    Optional<FieldMember> findByIdAndTeamId(Long fieldMemberId, Long teamId);

    @Query("select fm from FieldMember fm where fm.team.id =: teamId and fm.beforeMatching.id =: beforeMatchId and fm.anonymous = false ")
    List<FieldMember> findMatchFieldMembers(Long teamId, Long beforeMatchId);
}
