package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.request.*;
import me.coldrain.ninetyminute.dto.response.JwtTokenResponse;
import me.coldrain.ninetyminute.dto.response.MemberDuplicateResponse;
import me.coldrain.ninetyminute.entity.Ability;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.entity.MemberRoleEnum;
import me.coldrain.ninetyminute.exception.ErrorCode;
import me.coldrain.ninetyminute.repository.AbilityRepository;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AbilityRepository abilityRepository;
    private final AwsS3Service awsS3Service;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @Transactional
    public ResponseEntity<?> memberSignup(MemberRegisterRequest memberRegisterRequest) {
        Optional<Member> found = memberRepository.findByUsername(memberRegisterRequest.getEmail());
        if (found.isPresent()) {
            return new ResponseEntity<>("중복된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        if (!memberRegisterRequest.getPassword().equals(memberRegisterRequest.getConfirmpassword())) {
            return new ResponseEntity<>("재확인 비밀번호가 다릅니다.", HttpStatus.BAD_REQUEST);
        } else {
            MemberRoleEnum role = MemberRoleEnum.USER;

            Member member = new Member(memberRegisterRequest, role);
            member.encryptPassword(passwordEncoder);
            memberRepository.save(member);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    //아이디 중복 확인
    public ResponseEntity<?> duplicateCheckEmail(MemberEmailDuplicateRequest memberEmailDuplicateRequest) {
        MemberDuplicateResponse memberDuplicateResponse = new MemberDuplicateResponse();
        Optional<Member> found = memberRepository.findByUsername(memberEmailDuplicateRequest.getEmail());
        if (found.isPresent()) {
            memberDuplicateResponse.setExist(true);
            memberDuplicateResponse.setMessage("중복된 이메일입니다.");
        } else {
            memberDuplicateResponse.setExist(false);
            memberDuplicateResponse.setMessage("사용 가능한 이메일입니다.");
        }
        return new ResponseEntity<>(memberDuplicateResponse, HttpStatus.OK);
    }

    //닉네임 중복 확인
    public ResponseEntity<?> duplicateCheckNickname(MemberNicknameDuplicateRequest memberNicknameDuplicateRequest) {
        MemberDuplicateResponse memberDuplicateResponse = new MemberDuplicateResponse();
        Optional<Member> found = memberRepository.findByNickname(memberNicknameDuplicateRequest.getNickname());
        if (found.isPresent()) {
            memberDuplicateResponse.setExist(true);
            memberDuplicateResponse.setMessage("중복된 닉네임입니다.");
        } else {
            memberDuplicateResponse.setExist(false);
            memberDuplicateResponse.setMessage("사용 가능한 닉네임입니다.");
        }
        return new ResponseEntity<>(memberDuplicateResponse, HttpStatus.OK);
    }

    //회원정보 수정
    @Transactional
    public ResponseEntity<?> memberEdit(Long memberId,
                                        MemberEditRequest memberEditRequest) {
        try {
            Member member = memberRepository.findById(memberId).orElseThrow();
            Optional<Member> found = memberRepository.findByNickname(memberEditRequest.getNickname());
            if (found.isPresent()) {
                return new ResponseEntity<>("중복된 닉네임입니다.", HttpStatus.BAD_REQUEST);
            }

            if (member.getNickname() == null) {
                final Ability emptyAbility = abilityRepository.save(new Ability());
                member.newMemberUpdate(memberEditRequest, emptyAbility);
            } else {
                member.memberUpdate(memberEditRequest);
            }

            return new ResponseEntity<>(jwtTokenCreate(member), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    //회원 프로필 사진 업로드
    @Transactional
    public ResponseEntity<?> memberProFileImageEdit(Long memberId,
                                                    MultipartFile proFileImage) {
        try {
            Member member = memberRepository.findById(memberId).orElseThrow();

            if (member.getProfileName() == null && (proFileImage == null || proFileImage.isEmpty())) {
                Map<String, String> profileImg = new HashMap<>();
                profileImg.put("url", "/images/basicprofileimage.png");
                profileImg.put("transImgFileName", "basicImage");
                member.memberproFileImageUpdate(profileImg);

            } else if (member.getProfileName() == null) {
                Map<String, String> profileImg = awsS3Service.uploadFile(proFileImage);
                member.memberproFileImageUpdate(profileImg);
            } else {
                if (member.getProfileName().equals("basicImage") && (proFileImage == null || proFileImage.isEmpty())) {
                    return new ResponseEntity<>(HttpStatus.OK);

                } else if (member.getProfileName().equals("basicImage")) {
                    Map<String, String> profileImg = awsS3Service.uploadFile(proFileImage);
                    member.memberproFileImageUpdate(profileImg);

                } else if (!(proFileImage == null || proFileImage.isEmpty())) {
                    awsS3Service.deleteFile(member.getProfileName());
                    Map<String, String> profileImg = awsS3Service.uploadFile(proFileImage);
                    member.memberproFileImageUpdate(profileImg);
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    //로그인
    public ResponseEntity<?> memberLogin(MemberLoginRequest memberLoginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(), memberLoginRequest.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorCode.USERNAME_OR_PASSWORD_NOTFOUND.getMessage(), ErrorCode.USERNAME_OR_PASSWORD_NOTFOUND.getStatus());
        }
        Member member = memberRepository.findByUsername(memberLoginRequest.getEmail()).orElse(null);
        return new ResponseEntity<>(jwtTokenCreate(member), HttpStatus.CREATED);
    }

    //JWT 토큰 생성기
    private JwtTokenResponse jwtTokenCreate(Member member) {
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();

        if (member.getNickname() == null) {
            String accessToken = jwtTokenProvider.createnewAccessToken(member);
            jwtTokenResponse.setAccesstoken(accessToken);
            jwtTokenResponse.setFirst(true);
        } else {
            String accessToken = jwtTokenProvider.createnewAccessToken(member);
            jwtTokenResponse.setAccesstoken(accessToken);
        }
        return jwtTokenResponse;
    }
}
