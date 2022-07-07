package me.coldrain.ninetyminute.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.coldrain.ninetyminute.dto.request.MemberEditRequest;
import me.coldrain.ninetyminute.dto.request.MemberRegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Map;

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
    @Column(unique = true)
    private Long kakaoId;
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String nickname;
    @Column
    private String profileName;
    @Column
    private String profileUrl;
    @Column
    private String position;
    @Column
    private String contact;
    @Column
    private String phone;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    public Member(MemberRegisterRequest params, MemberRoleEnum role, Ability ability) {
        this.username = params.getEmail();
        this.password = params.getPassword();
        this.role = role;
        this.kakaoId = null;
        this.ability = ability;
    }

    public Member(String password, MemberRoleEnum role, Long kakaoId, Ability ability) {
        this.username = null;
        this.password = password;
        this.role = role;
        this.kakaoId = kakaoId;
        this.ability = ability;
    }

    public void memberUpdate(Map<String, String> profileImg, MemberEditRequest params){
        this.nickname = params.getNickname();
        this.profileName = profileImg.get("transImgFileName");
        this.profileUrl = profileImg.get("url");
        this.position = params.getPosition();
        this.contact = params.getContact();
        this.phone = params.getPhone();
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

}
