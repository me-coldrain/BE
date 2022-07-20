package me.coldrain.ninetyminute.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.response.JwtTokenResponse;
import me.coldrain.ninetyminute.entity.Ability;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.entity.MemberRoleEnum;
import me.coldrain.ninetyminute.repository.AbilityRepository;
import me.coldrain.ninetyminute.repository.MemberRepository;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import me.coldrain.ninetyminute.security.jwt.JwtTokenProvider;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class KakaoMemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AbilityRepository abilityRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        Long kakaoUserId = getKakaoUserInfo(accessToken);

        // 3. 카카오 회원 정보로 회원가입(이미 회원일 경우 회원정보 가져오기)
        Member kakaoMember = registerKakaoUserIfNeeded(kakaoUserId);

        // 4. 3번 과정 이후 생긴 회원 정보로 로그인 처리
        forceLogin(kakaoMember);

        // 5. JWT 토큰 생성
        JwtTokenResponse jwtTokenResponse = jwtTokenCreate(kakaoMember);

        return new ResponseEntity<>(jwtTokenResponse, HttpStatus.CREATED);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "3c2e867a60400604cd64199c1ec0227a");
        body.add("redirect_uri", "http://localhost:8080/api/members/kakao/login");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private Long getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("id").asLong();
//        Long id = jsonNode.get("id").asLong();
//        String nickname = jsonNode.get("properties").get("nickname").asText();
//        String email = jsonNode.get("kakao_account").get("email").asText();

//        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
//        return new KakaoUserInfoDto(id, nickname, email);
    }

    private Member registerKakaoUserIfNeeded(Long kakaoUserId) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Member kakaomember = memberRepository.findByKakaoId(kakaoUserId).orElse(null);
        if (kakaomember == null) {
            // 회원가입
            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            // role: 소셜 로그인 사용자
            MemberRoleEnum role = MemberRoleEnum.SOCIAL;

            kakaomember = new Member(encodedPassword, role, kakaoUserId);
            return memberRepository.save(kakaomember);
        }
        return kakaomember;
    }

    private void forceLogin(Member kakaoMember) {
        UserDetails userDetails = new UserDetailsImpl(kakaoMember);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private JwtTokenResponse jwtTokenCreate(Member kakaoMember) {
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();

        if (kakaoMember.getNickname() == null) {
            String accessToken = jwtTokenProvider.createnewAccessToken(kakaoMember);
            jwtTokenResponse.setAccesstoken(accessToken);
            jwtTokenResponse.setFirst(true);
        } else {
            String accessToken = jwtTokenProvider.createnewAccessToken(kakaoMember);
            jwtTokenResponse.setAccesstoken(accessToken);
        }
        return jwtTokenResponse;
    }
}
