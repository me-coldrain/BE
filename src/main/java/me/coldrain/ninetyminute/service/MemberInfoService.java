package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.response.MemberInfoResponse;
import me.coldrain.ninetyminute.dto.response.MyParticipationTeamListResponse;
import me.coldrain.ninetyminute.entity.*;
import me.coldrain.ninetyminute.repository.*;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberInfoService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final ParticipationRepository participationRepository;
    private final WeekdayRepository weekdayRepository;
    private final TimeRepository timeRepository;

    //회원정보 조회
    public ResponseEntity<?> memberInfoGet(Long memberId, UserDetailsImpl userDetails) {
        try{
            Member member = memberRepository.findById(memberId).orElseThrow();
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
        if(foundMember.isEmpty()) {
            return new ResponseEntity<>("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
        } else if (foundTeam.isEmpty()) {
            return new ResponseEntity<>("존재하지 않는 팀입니다.", HttpStatus.BAD_REQUEST);
        } else {
            Participation offerCancelTeam = participationRepository.findByTeamIdAndMemberIdFalse(memberId, teamId);
            participationRepository.delete(offerCancelTeam);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //참여한 경기 히스토리 조회
    public ResponseEntity<?> memberGameHistory(Long memberId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
