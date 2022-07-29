package me.coldrain.ninetyminute.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.request.*;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import me.coldrain.ninetyminute.service.KakaoMemberService;
import me.coldrain.ninetyminute.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    private final KakaoMemberService kakaoMemberService;

    //회원가입
    @PostMapping("/api/members/signup")
    public ResponseEntity<?> memberSignup(@RequestBody @Valid MemberRegisterRequest memberRegisterRequest) {
        return memberService.memberSignup(memberRegisterRequest);
    }

    //아이디 중복 확인
    @PostMapping("/api/members/email/exist")
    public ResponseEntity<?> duplicateCheckEmail(@RequestBody @Valid MemberEmailDuplicateRequest memberEmailDuplicateRequest) {
        return memberService.duplicateCheckEmail(memberEmailDuplicateRequest);
    }

    //로그인
    @PostMapping("/api/members/login")
    public ResponseEntity<?> memberLogin(@RequestBody @Valid MemberLoginRequest memberLoginRequest) {
        return memberService.memberLogin(memberLoginRequest);
    }

    //소셜로그인
    @GetMapping("/api/members/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        //프론트엔드 인가코드 요청 방법
        //https://kauth.kakao.com/oauth/authorize?client_id=3c2e867a60400604cd64199c1ec0227a&redirect_uri=https://프론트엔드도메인/kakao&response_type=code
        return kakaoMemberService.kakaoLogin(code);
    }

    //회원정보 수정
    @PatchMapping("/api/home/members/information")
    public ResponseEntity<?> memberEdit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestBody @Valid MemberEditRequest memberEditRequest) {
        return memberService.memberEdit(userDetails, memberEditRequest);
    }

    //닉네임 중복 확인
    @PostMapping("/api/home/members/nickname/exist")
    public ResponseEntity<?> duplicateCheckNickname(@RequestBody @Valid MemberNicknameDuplicateRequest memberNicknameDuplicateRequest) {
        return memberService.duplicateCheckNickname(memberNicknameDuplicateRequest);
    }

    //회원 프로필 사진 업로드
    @PatchMapping("/api/home/members/information/profileimage")
    public ResponseEntity<?> memberProFileImageEdit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    MultipartFile profileImageFile) {
        return memberService.memberProFileImageEdit(userDetails, profileImageFile);
    }
}
