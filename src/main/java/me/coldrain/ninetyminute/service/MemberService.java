package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.request.*;
import me.coldrain.ninetyminute.dto.response.JwtTokenResponse;
import me.coldrain.ninetyminute.dto.response.MemberDuplicateResponse;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.entity.MemberRoleEnum;
import me.coldrain.ninetyminute.exception.AuthenticationException;
import me.coldrain.ninetyminute.exception.ErrorCode;
import me.coldrain.ninetyminute.repository.MemberRepository;
import me.coldrain.ninetyminute.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    public ResponseEntity<?> memberSignup(MemberRegisterRequest memberRegisterRequest){
        if(!memberRegisterRequest.getPassword().equals(memberRegisterRequest.getConfirmPassword())){
            return new ResponseEntity<>("재확인 비밀번호가 다릅니다.",HttpStatus.BAD_REQUEST);

        } else {
            MemberRoleEnum role = MemberRoleEnum.USER;
            Member member = new Member(memberRegisterRequest, role);
            member.encryptPassword(passwordEncoder);
            memberRepository.save(member);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    //아이디 중복 확인
    public  ResponseEntity<?> duplicateCheckEmail(MemberEmailDuplicateRequest memberEmailDuplicateRequest){
        MemberDuplicateResponse memberDuplicateResponse = new MemberDuplicateResponse();
        Optional<Member> found = memberRepository.findByUsername(memberEmailDuplicateRequest.getEmail());
        if(found.isPresent()){
            memberDuplicateResponse.setExist(true);
            memberDuplicateResponse.setMessage("중복된 이메일입니다.");
        } else {
            memberDuplicateResponse.setExist(false);
            memberDuplicateResponse.setMessage("사용 가능한 이메일입니다.");
        }
        return new ResponseEntity<>(memberDuplicateResponse, HttpStatus.OK);
    }

    //닉네임 중복 확인
    public  ResponseEntity<?> duplicateCheckNickname(MemberNicknameDuplicateRequest memberNicknameDuplicateRequest){
        MemberDuplicateResponse memberDuplicateResponse = new MemberDuplicateResponse();
        Optional<Member> found = memberRepository.findByNickname(memberNicknameDuplicateRequest.getNickname());
        if(found.isPresent()){
            memberDuplicateResponse.setExist(true);
            memberDuplicateResponse.setMessage("중복된 닉네임입니다.");
        } else {
            memberDuplicateResponse.setExist(false);
            memberDuplicateResponse.setMessage("사용 가능한 닉네임입니다.");
        }
        return new ResponseEntity<>(memberDuplicateResponse, HttpStatus.OK);
    }

    //회원정보 수정
    public ResponseEntity<?> memberEdit(Long member_id,
                                        MultipartFile profileImageFile,
                                        MemberEditRequest memberEditRequest){
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 회원입니다."));

        if(member.getProfileUrl() == null){
            Map<String, String> proFileUrl = awsS3Service.uploadFile(profileImageFile);
            member.memberUpdate(proFileUrl, memberEditRequest);

        } else {
            awsS3Service.deleteFile(member.getProfilename());
            Map<String, String> proFileUrl = awsS3Service.uploadFile(profileImageFile);
            member.memberUpdate(proFileUrl, memberEditRequest);
        }
        return new ResponseEntity<>(jwtTokenCreate(member), HttpStatus.OK);
    }

    //로그인
    public ResponseEntity<?> memberLogin(MemberLoginRequest memberLoginRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(), memberLoginRequest.getPassword()));
        } catch (Exception e) {
            throw new AuthenticationException(ErrorCode.UsernameOrPasswordNotFoundException);
        }
        Member member = memberRepository.findByUsername(memberLoginRequest.getEmail()).orElse(null);
        return new ResponseEntity<>(jwtTokenCreate(member), HttpStatus.CREATED);
    }

    //JWT 토큰 생성기
    private JwtTokenResponse jwtTokenCreate(Member member){
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();

        if(member.getNickname() == null){
            String accessToken = jwtTokenProvider.createnewAccessToken(member);
            jwtTokenResponse.setAccesstoken(accessToken);
            jwtTokenResponse.setFirst(true);
        } else {
            String accessToken = jwtTokenProvider.createnewAccessToken(member);
            jwtTokenResponse.setAccesstoken(accessToken);
        }
//        jwtTokenResponse.setCode(201);
//        jwtTokenResponse.setMessage("Access토큰이 발급되었습니다.");
        return jwtTokenResponse;
    }
}
