package me.coldrain.ninetyminute.repository;


import me.coldrain.ninetyminute.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT T.question FROM Team T WHERE T.id = :teamId")
    Optional<String> findQuestionByTeamId(final Long teamId);

    Optional<Team> findByRecord_Id(Long recordId);

    @Query("select t from Team t where t.id = :teamId and t.deleted = false")
    Optional<Team> findByIdAndDeletedFalse(final Long teamId);
}
