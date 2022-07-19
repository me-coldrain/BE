package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByKakaoId(Long kakaoId);
    Optional<Member> findByAbility_Id(Long abilityId);
    @Query("SELECT m FROM Member m WHERE m.openTeam.id = :teamId")
    Optional<Member> findByOpenTeam(Long teamId);

    //포지션 랭킹 조회
    @Query("SELECT m FROM Member m ORDER BY m.ability.mvpPoint desc")
    List<Member> findAllByMvpPoint();
    @Query("SELECT m FROM Member m ORDER BY m.ability.charmingPoint desc")
    List<Member> findAllByCharmingPoint();
    @Query("SELECT m FROM Member m WHERE m.position = :striker ORDER BY m.ability.strikerPoint desc")
    List<Member> findAllByStrikerPoint(String striker);
    @Query("SELECT m FROM Member m WHERE m.position = :midfielder ORDER BY m.ability.midfielderPoint desc")
    List<Member> findAllByMidfielderPoint(String midfielder);
    @Query("SELECT m FROM Member m WHERE m.position = :defender ORDER BY m.ability.defenderPoint desc")
    List<Member> findAllByDefenderPoint(String defender);
    @Query("SELECT m FROM Member m WHERE m.position = :goalkeeper ORDER BY m.ability.goalkeeperPoint desc")
    List<Member> findAllByGoalkeeperPoint(String goalkeeper);
}
