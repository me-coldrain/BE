package me.coldrain.ninetyminute.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Member extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team openTeam; // 개설한 팀

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_id")
    private Ability ability; // 능력치

    private String username;

    private String password;

    private String nickname;

    private String profileUrl;

    private String position;

    private String contact;

    private String phone;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

}
