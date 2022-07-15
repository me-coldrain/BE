package me.coldrain.ninetyminute.repository;

import me.coldrain.ninetyminute.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByKakaoId(Long kakaoId);
    Optional<Member> findByAbility_Id(Long abilityId);
    @Query("SELECT m FROM Member m WHERE m.openTeam.id = :teamId")
    Optional<Member> findByOpenTeam(Long teamId);
}
