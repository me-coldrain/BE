package me.coldrain.ninetyminute.security;

import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.entity.MemberRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Member member;
//    private Long id;
//    private String userid;
//    private String nickname;
//    private UserRoleEnum role;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    public Member getUser() {
        return member;
    }

    public String getNickname() {
        return member.getNickname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        MemberRoleEnum role = member.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>(); //List인 이유 : 여러개의 권한을 가질 수 있다
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}