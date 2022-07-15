package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.response.MemberInfoResponse;
import me.coldrain.ninetyminute.dto.response.MyParticipationTeamListResponse;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.entity.Participation;
import me.coldrain.ninetyminute.entity.Time;
import me.coldrain.ninetyminute.entity.Weekday;
import me.coldrain.ninetyminute.repository.MemberRepository;
import me.coldrain.ninetyminute.repository.ParticipationRepository;
import me.coldrain.ninetyminute.repository.TimeRepository;
import me.coldrain.ninetyminute.repository.WeekdayRepository;
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

    //참여한 팀 조회
    public ResponseEntity<?> memberTeamGet(Long memberId) {
        List<MyParticipationTeamListResponse> myParticipationTeamListResponseList = new ArrayList<>();

        List<Participation> myTeamList = participationRepository.findAllByMemberIdTrue(memberId);
        for (Participation participation : myTeamList) {
            boolean captain = participation.getMember().getOpenTeam() != null;
            int headCount = participationRepository.findAllByTeamIdTrue(participation.getTeam().getId()).size();

            List<String> participationTeamWeekdays = new ArrayList<>();
            List<Weekday> participationTeamWeekdayList = weekdayRepository.findAllByTeamId(participation.getTeam().getId());
            for (Weekday weekday : participationTeamWeekdayList) {
                participationTeamWeekdays.add(weekday.getWeekday());
            }

            List<String> participationTeamTimes = new ArrayList<>();
            List<Time> participationTeamTimeList = timeRepository.findAllByTeamId(participation.getTeam().getId());
            for (Time time : participationTeamTimeList) {
                participationTeamTimes.add(time.getTime());
            }

            MyParticipationTeamListResponse myParticipationTeamListResponse = new MyParticipationTeamListResponse(
                    captain,
                    participation.getTeam().getId(),
                    participation.getTeam().getName(),
                    headCount,
                    participation.getTeam().getMainArea(),
                    participation.getTeam().getPreferredArea(),
                    participationTeamWeekdays,
                    participationTeamTimes,
                    participation.getTeam().getRecord().getWinRate(),
                    participation.getTeam().getRecruit(),
                    participation.getTeam().getMatches(),
                    participation.getTeam().getRecord().getTotalGameCount(),
                    participation.getTeam().getRecord().getWinCount(),
                    participation.getTeam().getRecord().getDrawCount(),
                    participation.getTeam().getRecord().getLoseCount(),
                    participation.getTeam().getCreatedDate(),
                    participation.getTeam().getModifiedDate()
            );
            myParticipationTeamListResponseList.add(myParticipationTeamListResponse);
        }
        return new ResponseEntity<>(myParticipationTeamListResponseList, HttpStatus.OK);
    }

    //참여 신청중인 팀 조회
    public ResponseEntity<?> offerTeamGet(Long memberId) {
        List<MyParticipationTeamListResponse> myParticipationTeamListResponseList = new ArrayList<>();

        List<Participation> myTeamList = participationRepository.findAllByMemberIdFalse(memberId);
        for (Participation participation : myTeamList) {
            boolean captain = participation.getMember().getOpenTeam() != null;
            int headCount = participationRepository.findAllByTeamIdTrue(participation.getTeam().getId()).size();

            List<String> participationTeamWeekdays = new ArrayList<>();
            List<Weekday> participationTeamWeekdayList = weekdayRepository.findAllByTeamId(participation.getTeam().getId());
            for (Weekday weekday : participationTeamWeekdayList) {
                participationTeamWeekdays.add(weekday.getWeekday());
            }

            List<String> participationTeamTimes = new ArrayList<>();
            List<Time> participationTeamTimeList = timeRepository.findAllByTeamId(participation.getTeam().getId());
            for (Time time : participationTeamTimeList) {
                participationTeamTimes.add(time.getTime());
            }

            MyParticipationTeamListResponse myParticipationTeamListResponse = new MyParticipationTeamListResponse(
                    captain,
                    participation.getTeam().getId(),
                    participation.getTeam().getName(),
                    headCount,
                    participation.getTeam().getMainArea(),
                    participation.getTeam().getPreferredArea(),
                    participationTeamWeekdays,
                    participationTeamTimes,
                    participation.getTeam().getRecord().getWinRate(),
                    participation.getTeam().getRecruit(),
                    participation.getTeam().getMatches(),
                    participation.getTeam().getRecord().getTotalGameCount(),
                    participation.getTeam().getRecord().getWinCount(),
                    participation.getTeam().getRecord().getDrawCount(),
                    participation.getTeam().getRecord().getLoseCount(),
                    participation.getTeam().getCreatedDate(),
                    participation.getTeam().getModifiedDate()
            );
            myParticipationTeamListResponseList.add(myParticipationTeamListResponse);
        }
        return new ResponseEntity<>(myParticipationTeamListResponseList, HttpStatus.OK);
    }

    //참여 신청중인 팀 신청취소
    @Transactional
    public ResponseEntity<?> offerCancelTeam(Long memberId, Long teamId) {
        Participation offerCancelTeam = participationRepository.findByTeamIdAndMemberIdFalse(memberId, teamId);
        participationRepository.delete(offerCancelTeam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //참여한 경기 히스토리 조회
    public ResponseEntity<?> memberGameHistory(Long memberId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
