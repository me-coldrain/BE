package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.response.MemberInfoResponse;
import me.coldrain.ninetyminute.entity.*;
import me.coldrain.ninetyminute.repository.*;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberInfoService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final ParticipationRepository participationRepository;
    private final MemberService memberService;

    //회원정보 조회
    public ResponseEntity<?> memberInfoGet(Long memberId, UserDetailsImpl userDetails) {
        try {
            Member member = memberRepository.findById(memberId).orElseThrow();

            if (memberService.secessionMemberCheck(member.getUsername())) {
                return new ResponseEntity<>("탈퇴 처리된 회원 입니다.", HttpStatus.BAD_REQUEST);
            }

            boolean myInfo = memberId.equals(userDetails.getUser().getId());

            MemberInfoResponse memberInfoResponse = new MemberInfoResponse(
                    myInfo,
                    member.getNickname(), member.getProfileUrl(), member.getContact(), member.getPhone(), member.getPosition(),
                    member.getAbility().getMvpPoint(), 0, 0,
                    member.getAbility().getStrikerPoint(),
                    member.getAbility().getMidfielderPoint(),
                    member.getAbility().getDefenderPoint(),
                    member.getAbility().getGoalkeeperPoint(),
                    member.getAbility().getCharmingPoint());
            return new ResponseEntity<>(memberInfoResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    //참여 신청중인 팀 신청취소
    @Transactional
    public ResponseEntity<?> offerCancelTeam(Long memberId, Long teamId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        Optional<Team> foundTeam = teamRepository.findById(teamId);
        if (foundMember.isEmpty()) {
            return new ResponseEntity<>("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
        } else if (foundTeam.isEmpty()) {
            return new ResponseEntity<>("존재하지 않는 팀입니다.", HttpStatus.BAD_REQUEST);
        } else {
            Participation offerCancelTeam = participationRepository.findByTeamIdAndMemberIdFalse(memberId, teamId).orElseThrow();
            participationRepository.delete(offerCancelTeam);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //참여한 경기 히스토리 조회
    public ResponseEntity<?> memberGameHistory(Long memberId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //회원탈퇴
    @Transactional
    public ResponseEntity<?> memberSecession(UserDetailsImpl userDetails) {
        Member member = memberRepository.findById(userDetails.getUser().getId()).orElse(null);

        if (member != null) {
            if (member.isSecessionState()) {
                return new ResponseEntity<>("탈퇴 처리된 회원 입니다.", HttpStatus.BAD_REQUEST);
            }

            List<Participation> participation = participationRepository.findAllByMemberIdTrue(member.getId());

            if (member.getOpenTeam() != null) {
                return new ResponseEntity<>("팀을 해체하고 회원탈퇴를 진행해주세요.", HttpStatus.BAD_REQUEST);
            } else if (participation.size() != 0) {
                return new ResponseEntity<>("가입한 팀을 탈퇴하고 회원탈퇴를 진행해주세요.", HttpStatus.BAD_REQUEST);
            }

            String username = "secession-" + member.getUsername();
            String nickname = "secession-" + member.getNickname();
            member.memberSecession(username, nickname);

            return new ResponseEntity<>("회원탈퇴가 완료 되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("회원이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
