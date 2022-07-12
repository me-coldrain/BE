package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.response.MyInfoResponse;
import me.coldrain.ninetyminute.dto.response.MyParticipationTeamListResponse;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.entity.Participation;
import me.coldrain.ninetyminute.repository.MemberRepository;
import me.coldrain.ninetyminute.repository.ParticipationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyInfoService {
    private final MemberRepository memberRepository;
    private final ParticipationRepository participationRepository;

    //회원정보 조회
    public ResponseEntity<?> myInfoGet(Long member_id) {
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new NullPointerException("등록된 사용자가 아닙니다."));

        MyInfoResponse myInfoResponse = new MyInfoResponse(
                member.getNickname(), member.getContact(), member.getPhone(), member.getPosition(),
                member.getAbility().getMvpPoint(), 0, 0,
                member.getAbility().getStrikerPoint(),
                member.getAbility().getMidfielderPoint(),
                member.getAbility().getDefenderPoint(),
                member.getAbility().getGoalkeeperPoint(),
                member.getAbility().getCharmingPoint());
        return new ResponseEntity<>(myInfoResponse, HttpStatus.OK);
    }

    //참여한 팀 조회
    public ResponseEntity<?> myTeamGet(Long member_id) {
        List<MyParticipationTeamListResponse> myParticipationTeamListResponseList = new ArrayList<>();

        Member member = memberRepository.findById(member_id).orElseThrow(() -> new NullPointerException("등록된 사용자가 아닙니다."));
        if (member.getOpenTeam() != null) {
            int headCount = participationRepository.findAllByTeamId(member.getOpenTeam().getId()).size();

            MyParticipationTeamListResponse myOpenTeamTeamResponse = new MyParticipationTeamListResponse(
                    member.getOpenTeam().getId(),
                    member.getOpenTeam().getName(),
                    headCount,
                    member.getOpenTeam().getMainArea(),
                    member.getOpenTeam().getPreferredArea(),
                    member.getOpenTeam().getWeekdays(),
                    member.getOpenTeam().getTimeList(),
                    member.getOpenTeam().getRecord().getWinRate(),
                    true,
                    true,
                    member.getOpenTeam().getRecord().getTotalGameCount(),
                    member.getOpenTeam().getRecord().getWinCount(),
                    member.getOpenTeam().getRecord().getDrawCount(),
                    member.getOpenTeam().getRecord().getLoseCount(),
                    member.getOpenTeam().getCreatedDate(),
                    member.getOpenTeam().getModifiedDate()
            );
            myParticipationTeamListResponseList.add(myOpenTeamTeamResponse);
        }

        List<Participation> myTeamList = participationRepository.findAllByMemberIdTrue(member_id);
        for (Participation participation : myTeamList) {
            int headCount = participationRepository.findAllByTeamId(participation.getTeam().getId()).size();

            MyParticipationTeamListResponse myParticipationTeamListResponse = new MyParticipationTeamListResponse(
                    participation.getTeam().getId(),
                    participation.getTeam().getName(),
                    headCount,
                    participation.getTeam().getMainArea(),
                    participation.getTeam().getPreferredArea(),
                    participation.getTeam().getWeekdays(),
                    participation.getTeam().getTimeList(),
                    participation.getTeam().getRecord().getWinRate(),
                    true,
                    true,
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
    public ResponseEntity<?> offerTeamGet(Long member_id) {
        List<MyParticipationTeamListResponse> myParticipationTeamListResponseList = new ArrayList<>();

        List<Participation> myTeamList = participationRepository.findAllByMemberIdFalse(member_id);
        for (Participation participation : myTeamList) {
            int headCount = participationRepository.findAllByTeamId(participation.getTeam().getId()).size();

            MyParticipationTeamListResponse myParticipationTeamListResponse = new MyParticipationTeamListResponse(
                    participation.getTeam().getId(),
                    participation.getTeam().getName(),
                    headCount,
                    participation.getTeam().getMainArea(),
                    participation.getTeam().getPreferredArea(),
                    participation.getTeam().getWeekdays(),
                    participation.getTeam().getTimeList(),
                    participation.getTeam().getRecord().getWinRate(),
                    true,
                    true,
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
    public ResponseEntity<?> offerCancelTeam(Long member_id, Long team_id) {
        Participation offerCancelTeam = participationRepository.findByTeamIdAndMemberIdFalse(member_id, team_id);
        participationRepository.delete(offerCancelTeam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //참여한 경기 히스토리 조회
    public ResponseEntity<?> myGameHistory(Long member_id) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
