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

    @OneToOne(fetch = FetchType.EAGER)
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
    @Column
    private boolean secessionState;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    public void setOpenTeam(Team openTeam) {
        this.openTeam = openTeam;
    }

    public Member(MemberRegisterRequest params, MemberRoleEnum role) {
        this.username = params.getEmail();
        this.password = params.getPassword();
        this.role = role;
        this.kakaoId = null;
//        this.secessionState = false;
    }

    public Member(String username, String password, MemberRoleEnum role, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.kakaoId = kakaoId;
//        this.secessionState = false;
    }

    public void newMemberUpdate(MemberEditRequest params, Ability ability) {
        this.nickname = params.getNickname();
        this.position = params.getPosition();
        this.contact = params.getContact();
        this.phone = params.getPhone();
        this.ability = ability;
    }

    public void memberUpdate(MemberEditRequest params) {
        this.nickname = params.getNickname();
        this.position = params.getPosition();
        this.contact = params.getContact();
        this.phone = params.getPhone();
    }

    public void memberProFileImageUpdate(Map<String, String> profileImg) {
        this.profileName = profileImg.get("transImgFileName");
        this.profileUrl = profileImg.get("url");
    }

    public void memberSecession(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.secessionState = true;
    }

    public void kakaoMemberSecession(String username, String nickname, Long kakaoId) {
        this.username = username;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.secessionState = true;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

}