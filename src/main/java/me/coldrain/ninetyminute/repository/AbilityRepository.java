package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

    //개별 랭킹 확인을 위한 포지션별 랭킹 전체 조회
    List<Ability> findAllByOrderByMvpPointDesc();

    List<Ability> findAllByOrderByCharmingPointDesc();

    List<Ability> findAllByOrderByStrikerPointDesc();

    List<Ability> findAllByOrderByMidfielderPointDesc();

    List<Ability> findAllByOrderByDefenderPointDesc();

    List<Ability> findAllByOrderByGoalkeeperPointDesc();
}
