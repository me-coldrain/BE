package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.AfterMatching;
import me.coldrain.ninetyminute.entity.BeforeMatching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AfterMatchingRepository extends JpaRepository<AfterMatching, Long> {

//    Optional<AfterMatching> findByBeforeMatchingAndAdmitStatusTrue(BeforeMatching beforeMatching);

    @Query("select am from AfterMatching am where am.beforeMatching.id = :beforeMatchingId and am.admitStatus = true")
    Optional<AfterMatching> findByBeforeMatchingIdAdmitStatusTrue(Long beforeMatchingId);

    @Query("select am from AfterMatching am where am.beforeMatching.id = :beforeMatchingId and am.admitStatus = false ")
    Optional<AfterMatching> findByBeforeMatchingIdAdmitStatusFalse(Long beforeMatchingId);


    @Query("select am from AfterMatching am where am.id = :afterMatchId and am.admitStatus = true")
    Optional<AfterMatching> findByIdAndAdmitTrue(Long afterMatchId);

    @Query("select am from AfterMatching am where am.beforeMatching.teamName = :teamName and am.result = '승리' ")
    List<AfterMatching> findAllByWinHistory(String teamName);

}
