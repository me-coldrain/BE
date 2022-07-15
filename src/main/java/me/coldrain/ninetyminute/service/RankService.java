package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.response.MyRankResponse;
import me.coldrain.ninetyminute.dto.response.MyTeamRankResponse;
import me.coldrain.ninetyminute.dto.response.RankerMemberResponse;
import me.coldrain.ninetyminute.dto.response.RankerTeamResponse;
import me.coldrain.ninetyminute.entity.*;
import me.coldrain.ninetyminute.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RankService {
    private final MemberRepository memberRepository;
    private final AbilityRepository abilityRepository;
    private final TeamRepository teamRepository;
    private final RecordRepository recordRepository;
    private final ParticipationRepository participationRepository;

    //팀 랭킹 조회
    public ResponseEntity<?> teamRankGet() {
        List<RankerTeamResponse> rankerTeamResponsesList = new ArrayList<>();

        List<Record> Top10TeamList = recordRepository.findFirst10ByOrderByWinPointDesc();
        for (Record record_id : Top10TeamList) {
            Team rankerTeam = teamRepository.findByRecord_Id(record_id.getId()).orElseThrow(
                    () -> new NullPointerException("존재하지 않는 팀 입니다."));

            RankerTeamResponse rankerTeamResponse = new RankerTeamResponse(
                    rankerTeam.getId(),
                    rankerTeam.getMainArea(),
                    rankerTeam.getName(),
                    rankerTeam.getRecord().getWinPoint()
            );
            rankerTeamResponsesList.add(rankerTeamResponse);
        }
        return new ResponseEntity<>(rankerTeamResponsesList, HttpStatus.OK);
    }

    //개인 포지션 랭킹 조회
    public ResponseEntity<?> memberRankGet(String ability) {
        List<RankerMemberResponse> rankerMemberResponseList = new ArrayList<>();

        switch (ability) {

            case "mvp":
                List<Ability> Top10MvpPointMemberList = abilityRepository.findFirst10ByOrderByMvpPointDesc();
                for (Ability ability_id : Top10MvpPointMemberList) {
                    Member rankerMember = memberRepository.findByAbility_Id(ability_id.getId()).orElseThrow(
                            () -> new NullPointerException("존재하지 않는 회원 입니다."));

                    RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                            rankerMember.getId(),
                            rankerMember.getProfileUrl(),
                            rankerMember.getNickname(),
                            rankerMember.getPosition(),
                            rankerMember.getAbility().getMvpPoint(),
                            rankerMember.getAbility().getMvpPoint()
                    );
                    rankerMemberResponseList.add(rankerMemberResponse);
                }
                break;

            case "striker":
                List<Ability> Top10StrikerPointMemberList = abilityRepository.findFirst10ByOrderByStrikerPointDesc();
                for (Ability ability_id : Top10StrikerPointMemberList) {
                    Member rankerMember = memberRepository.findByAbility_Id(ability_id.getId()).orElseThrow(
                            () -> new NullPointerException("존재하지 않는 회원 입니다."));

                    if (rankerMember.getPosition().equals("striker")) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerMember.getId(),
                                rankerMember.getProfileUrl(),
                                rankerMember.getNickname(),
                                rankerMember.getPosition(),
                                rankerMember.getAbility().getMvpPoint(),
                                rankerMember.getAbility().getStrikerPoint()
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }
                }
                break;

            case "midfielder":
                List<Ability> Top10midfielderPointMemberList = abilityRepository.findFirst10ByOrderByMidfielderPointDesc();
                for (Ability ability_id : Top10midfielderPointMemberList) {
                    Member rankerMember = memberRepository.findByAbility_Id(ability_id.getId()).orElseThrow(
                            () -> new NullPointerException("존재하지 않는 회원 입니다."));

                    if (rankerMember.getPosition().equals("midfielder")) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerMember.getId(),
                                rankerMember.getProfileUrl(),
                                rankerMember.getNickname(),
                                rankerMember.getPosition(),
                                rankerMember.getAbility().getMvpPoint(),
                                rankerMember.getAbility().getMidfielderPoint()
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }
                }
                break;

            case "defender":
                List<Ability> Top10defenderPointMemberList = abilityRepository.findFirst10ByOrderByDefenderPointDesc();
                for (Ability ability_id : Top10defenderPointMemberList) {
                    Member rankerMember = memberRepository.findByAbility_Id(ability_id.getId()).orElseThrow(
                            () -> new NullPointerException("존재하지 않는 회원 입니다."));

                    if (rankerMember.getPosition().equals("defender")) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerMember.getId(),
                                rankerMember.getProfileUrl(),
                                rankerMember.getNickname(),
                                rankerMember.getPosition(),
                                rankerMember.getAbility().getMvpPoint(),
                                rankerMember.getAbility().getDefenderPoint()
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }
                }
                break;

            case "goalkeeper":
                List<Ability> Top10goalkeeperPointMemberList = abilityRepository.findFirst10ByOrderByGoalkeeperPointDesc();
                for (Ability ability_id : Top10goalkeeperPointMemberList) {
                    Member rankerMember = memberRepository.findByAbility_Id(ability_id.getId()).orElseThrow(
                            () -> new NullPointerException("존재하지 않는 회원 입니다."));

                    if (rankerMember.getPosition().equals("goalkeeper")) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerMember.getId(),
                                rankerMember.getProfileUrl(),
                                rankerMember.getNickname(),
                                rankerMember.getPosition(),
                                rankerMember.getAbility().getMvpPoint(),
                                rankerMember.getAbility().getGoalkeeperPoint()
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }
                }
                break;

            case "charming":
                List<Ability> Top10charmingPointMemberList = abilityRepository.findFirst10ByOrderByCharmingPointDesc();
                for (Ability ability_id : Top10charmingPointMemberList) {
                    Member rankerMember = memberRepository.findByAbility_Id(ability_id.getId()).orElseThrow(
                            () -> new NullPointerException("존재하지 않는 회원 입니다."));

                    RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                            rankerMember.getId(),
                            rankerMember.getProfileUrl(),
                            rankerMember.getNickname(),
                            rankerMember.getPosition(),
                            rankerMember.getAbility().getMvpPoint(),
                            rankerMember.getAbility().getCharmingPoint()
                    );
                    rankerMemberResponseList.add(rankerMemberResponse);
                }
                break;
        }
        return new ResponseEntity<>(rankerMemberResponseList, HttpStatus.OK);
    }

    //로그인 사용자 개인 랭킹
    public ResponseEntity<?> myRankGet(Long member_id) {
        List<MyRankResponse> myRankResponseList = new ArrayList<>();

        Member myRank = memberRepository.findById(member_id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 회원입니다."));
        String position = myRank.getPosition();

        switch (position) {
            case "striker":
                List<Ability> myStrikerPointRank = abilityRepository.findAllByOrderByStrikerPointDesc();

                MyRankResponse myStrikerRankResponse = new MyRankResponse(
                        myRank.getId(),
                        myRank.getProfileUrl(),
                        myRank.getNickname(),
                        myRank.getPosition(),
                        myRank.getAbility().getMvpPoint(),
                        myRank.getAbility().getStrikerPoint(),
                        myStrikerPointRank.indexOf(myRank.getAbility()) + 1
                );
                myRankResponseList.add(myStrikerRankResponse);
                break;

            case "midfielder":
                List<Ability> myMidfielderPointRank = abilityRepository.findAllByOrderByMidfielderPointDesc();

                MyRankResponse myMidfielderRankResponse = new MyRankResponse(
                        myRank.getId(),
                        myRank.getProfileUrl(),
                        myRank.getNickname(),
                        myRank.getPosition(),
                        myRank.getAbility().getMvpPoint(),
                        myRank.getAbility().getMidfielderPoint(),
                        myMidfielderPointRank.indexOf(myRank.getAbility()) + 1
                );
                myRankResponseList.add(myMidfielderRankResponse);
                break;

            case "defender":
                List<Ability> myDefenderPointRank = abilityRepository.findAllByOrderByDefenderPointDesc();

                MyRankResponse myDefenderRankResponse = new MyRankResponse(
                        myRank.getId(),
                        myRank.getProfileUrl(),
                        myRank.getNickname(),
                        myRank.getPosition(),
                        myRank.getAbility().getMvpPoint(),
                        myRank.getAbility().getDefenderPoint(),
                        myDefenderPointRank.indexOf(myRank.getAbility()) + 1
                );
                myRankResponseList.add(myDefenderRankResponse);
                break;

            case "goalkeeper":
                List<Ability> myGoalkeeperPointRank = abilityRepository.findAllByOrderByGoalkeeperPointDesc();

                MyRankResponse myGoalkeeperRankResponse = new MyRankResponse(
                        myRank.getId(),
                        myRank.getProfileUrl(),
                        myRank.getNickname(),
                        myRank.getPosition(),
                        myRank.getAbility().getMvpPoint(),
                        myRank.getAbility().getGoalkeeperPoint(),
                        myGoalkeeperPointRank.indexOf(myRank.getAbility()) + 1
                );
                myRankResponseList.add(myGoalkeeperRankResponse);
                break;
        }
        return new ResponseEntity<>(myRankResponseList, HttpStatus.OK);
    }

    //로그인 사용자가 참여하고 있는 팀의 랭킹 조회
    public ResponseEntity<?> myTeamRankGet(Long member_id) {
        List<MyTeamRankResponse> myTeamRankResponseList = new ArrayList<>();
        List<Record> myTeamWinPointRank = recordRepository.findAllByOrderByWinPointDesc();

        Member myOpenTeam = memberRepository.findById(member_id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 사용자 입니다."));
        if (myOpenTeam.getOpenTeam() != null) {
            MyTeamRankResponse myTeamRankResponse = new MyTeamRankResponse(
                    myOpenTeam.getOpenTeam().getId(),
                    myOpenTeam.getOpenTeam().getMainArea(),
                    myOpenTeam.getOpenTeam().getName(),
                    myOpenTeam.getOpenTeam().getRecord().getWinPoint(),
                    myTeamWinPointRank.indexOf(myOpenTeam.getOpenTeam().getRecord()) + 1
            );
            myTeamRankResponseList.add(myTeamRankResponse);
        }

        List<Participation> myTeam = participationRepository.findAllByMemberIdTrue(member_id);
        for (Participation participation : myTeam) {
            MyTeamRankResponse myTeamRankResponse = new MyTeamRankResponse(
                    participation.getTeam().getId(),
                    participation.getTeam().getMainArea(),
                    participation.getTeam().getName(),
                    participation.getTeam().getRecord().getWinPoint(),
                    myTeamWinPointRank.indexOf(participation.getTeam().getRecord()) + 1
            );
            myTeamRankResponseList.add(myTeamRankResponse);
        }
        return new ResponseEntity<>(myTeamRankResponseList, HttpStatus.OK);
    }
}
