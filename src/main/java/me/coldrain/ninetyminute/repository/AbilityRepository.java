package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

    //TOP10 개인 랭킹 조회
    @Query("SELECT A.id FROM Ability A")
    List<Long> findFirst10ByOrderByMvpPointDesc();

    @Query("SELECT A.id FROM Ability A")
    List<Long> findFirst10ByOrderByStrikerPointDesc();

    @Query("SELECT A.id FROM Ability A")
    List<Long> findFirst10ByOrderByMidfielderPointDesc();

    @Query("SELECT A.id FROM Ability A")
    List<Long> findFirst10ByOrderByDefenderPointDescDesc();

    @Query("SELECT A.id FROM Ability A")
    List<Long> findFirst10ByOrderByGoalkeeperPointDesc();

    @Query("SELECT A.id FROM Ability A")
    List<Long> findFirst10ByOrderByCharmingPointDesc();

    //개별 랭킹 확인을 위한 포지션별 랭킹 전체 조회
    @Query("SELECT A.id FROM Ability A")
    List<Long> findAllByOrderByStrikerPointDesc();

    @Query("SELECT A.id FROM Ability A")
    List<Long> findAllByOrderByMidfielderPointDesc();

    @Query("SELECT A.id FROM Ability A")
    List<Long> findAllByOrderByDefenderPointDesc();

    @Query("SELECT A.id FROM Ability A")
    List<Long> findAllByOrderByGoalkeeperPointDesc();
}
