package me.coldrain.ninetyminute.security;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(userid).orElseThrow(
                ()-> new UsernameNotFoundException("등록되지 않은 사용자 입니다"));
        return new UserDetailsImpl(member);
    }
}
