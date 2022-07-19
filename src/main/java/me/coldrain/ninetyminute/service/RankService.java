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
        int rank = 1;

        List<Record> rankerTeamList = recordRepository.findAllByOrderByWinPointDescWinRateDesc();
        for (int i = 0; i < rankerTeamList.size(); i++) {

            Team rankerTeam = teamRepository.findByRecord_Id(rankerTeamList.get(i).getId())
                    .orElseThrow(() -> new NullPointerException("존재하지 않는 팀 입니다."));

            if (i == 0) {
                RankerTeamResponse rankerTeamResponse = new RankerTeamResponse(
                        rankerTeam.getId(),
                        rankerTeam.getMainArea(),
                        rankerTeam.getName(),
                        rankerTeam.getRecord().getWinPoint(),
                        rank
                );
                rankerTeamResponsesList.add(rankerTeamResponse);
            } else if (rankerTeamList.get(i - 1).getWinPoint().equals(rankerTeamList.get(i).getWinPoint()) &&
                    rankerTeamList.get(i - 1).getWinRate().equals(rankerTeamList.get(i).getWinRate())) {
                RankerTeamResponse rankerTeamResponse = new RankerTeamResponse(
                        rankerTeam.getId(),
                        rankerTeam.getMainArea(),
                        rankerTeam.getName(),
                        rankerTeam.getRecord().getWinPoint(),
                        rank
                );
                rankerTeamResponsesList.add(rankerTeamResponse);
            } else if (rankerTeamList.get(i - 1).getWinPoint().equals(rankerTeamList.get(i).getWinPoint()) &&
                    !rankerTeamList.get(i - 1).getWinRate().equals(rankerTeamList.get(i).getWinRate())) {
                rank++;
                RankerTeamResponse rankerTeamResponse = new RankerTeamResponse(
                        rankerTeam.getId(),
                        rankerTeam.getMainArea(),
                        rankerTeam.getName(),
                        rankerTeam.getRecord().getWinPoint(),
                        rank
                );
                rankerTeamResponsesList.add(rankerTeamResponse);
            } else {
                rank++;
                RankerTeamResponse rankerTeamResponse = new RankerTeamResponse(
                        rankerTeam.getId(),
                        rankerTeam.getMainArea(),
                        rankerTeam.getName(),
                        rankerTeam.getRecord().getWinPoint(),
                        rank
                );
                rankerTeamResponsesList.add(rankerTeamResponse);
            }

            if (rankerTeamResponsesList.size() == 10) {
                break;
            }
        }
        return new ResponseEntity<>(rankerTeamResponsesList, HttpStatus.OK);
    }

    //개인 포지션 랭킹 조회
    public ResponseEntity<?> memberRankGet(String ability) {
        List<RankerMemberResponse> rankerMemberResponseList = new ArrayList<>();

        switch (ability) {

            case "mvp":
                int mvpRank = 1;

                List<Member> rankerMvpPointMemberList = memberRepository.findAllByMvpPoint();
                for (int i = 0; i < rankerMvpPointMemberList.size(); i++) {

                    if (i == 0) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerMvpPointMemberList.get(i).getId(),
                                rankerMvpPointMemberList.get(i).getProfileUrl(),
                                rankerMvpPointMemberList.get(i).getNickname(),
                                rankerMvpPointMemberList.get(i).getPosition(),
                                rankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                mvpRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else if (rankerMvpPointMemberList.get(i - 1).getAbility().getMvpPoint()
                            .equals(rankerMvpPointMemberList.get(i).getAbility().getMvpPoint())) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerMvpPointMemberList.get(i).getId(),
                                rankerMvpPointMemberList.get(i).getProfileUrl(),
                                rankerMvpPointMemberList.get(i).getNickname(),
                                rankerMvpPointMemberList.get(i).getPosition(),
                                rankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                mvpRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else {
                        mvpRank++;
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerMvpPointMemberList.get(i).getId(),
                                rankerMvpPointMemberList.get(i).getProfileUrl(),
                                rankerMvpPointMemberList.get(i).getNickname(),
                                rankerMvpPointMemberList.get(i).getPosition(),
                                rankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                mvpRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }

                    if (rankerMemberResponseList.size() == 10) {
                        break;
                    }
                }
                break;

            case "striker":
                int strikerRank = 1;

                List<Member> rankStrikerPointMemberList = memberRepository.findAllByStrikerPoint(ability);
                for (int i = 0; i < rankStrikerPointMemberList.size(); i++) {

                    if (i == 0) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankStrikerPointMemberList.get(i).getId(),
                                rankStrikerPointMemberList.get(i).getProfileUrl(),
                                rankStrikerPointMemberList.get(i).getNickname(),
                                rankStrikerPointMemberList.get(i).getPosition(),
                                rankStrikerPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankStrikerPointMemberList.get(i).getAbility().getStrikerPoint(),
                                strikerRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else if (rankStrikerPointMemberList.get(i - 1).getAbility().getStrikerPoint()
                            .equals(rankStrikerPointMemberList.get(i).getAbility().getStrikerPoint())) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankStrikerPointMemberList.get(i).getId(),
                                rankStrikerPointMemberList.get(i).getProfileUrl(),
                                rankStrikerPointMemberList.get(i).getNickname(),
                                rankStrikerPointMemberList.get(i).getPosition(),
                                rankStrikerPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankStrikerPointMemberList.get(i).getAbility().getStrikerPoint(),
                                strikerRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else {
                        strikerRank++;
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankStrikerPointMemberList.get(i).getId(),
                                rankStrikerPointMemberList.get(i).getProfileUrl(),
                                rankStrikerPointMemberList.get(i).getNickname(),
                                rankStrikerPointMemberList.get(i).getPosition(),
                                rankStrikerPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankStrikerPointMemberList.get(i).getAbility().getStrikerPoint(),
                                strikerRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }

                    if (rankerMemberResponseList.size() ==10) {
                        break;
                    }
                }
                break;

            case "midfielder":
                int midfielderRank = 1;

                List<Member> rankMidfielderPointMemberList = memberRepository.findAllByMidfielderPoint(ability);
                for (int i = 0; i < rankMidfielderPointMemberList.size(); i++) {

                    if (i == 0) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankMidfielderPointMemberList.get(i).getId(),
                                rankMidfielderPointMemberList.get(i).getProfileUrl(),
                                rankMidfielderPointMemberList.get(i).getNickname(),
                                rankMidfielderPointMemberList.get(i).getPosition(),
                                rankMidfielderPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankMidfielderPointMemberList.get(i).getAbility().getMidfielderPoint(),
                                midfielderRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else if (rankMidfielderPointMemberList.get(i - 1).getAbility().getMidfielderPoint()
                            .equals(rankMidfielderPointMemberList.get(i).getAbility().getMidfielderPoint())) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankMidfielderPointMemberList.get(i).getId(),
                                rankMidfielderPointMemberList.get(i).getProfileUrl(),
                                rankMidfielderPointMemberList.get(i).getNickname(),
                                rankMidfielderPointMemberList.get(i).getPosition(),
                                rankMidfielderPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankMidfielderPointMemberList.get(i).getAbility().getMidfielderPoint(),
                                midfielderRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else {
                        midfielderRank++;
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankMidfielderPointMemberList.get(i).getId(),
                                rankMidfielderPointMemberList.get(i).getProfileUrl(),
                                rankMidfielderPointMemberList.get(i).getNickname(),
                                rankMidfielderPointMemberList.get(i).getPosition(),
                                rankMidfielderPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankMidfielderPointMemberList.get(i).getAbility().getMidfielderPoint(),
                                midfielderRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }

                    if (rankerMemberResponseList.size() ==10) {
                        break;
                    }
                }
                break;

            case "defender":
                int defenderRank = 1;

                List<Member> rankDefenderPointMemberList = memberRepository.findAllByDefenderPoint(ability);
                for (int i = 0; i < rankDefenderPointMemberList.size(); i++) {

                    if (i == 0) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankDefenderPointMemberList.get(i).getId(),
                                rankDefenderPointMemberList.get(i).getProfileUrl(),
                                rankDefenderPointMemberList.get(i).getNickname(),
                                rankDefenderPointMemberList.get(i).getPosition(),
                                rankDefenderPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankDefenderPointMemberList.get(i).getAbility().getDefenderPoint(),
                                defenderRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else if (rankDefenderPointMemberList.get(i - 1).getAbility().getDefenderPoint()
                            .equals(rankDefenderPointMemberList.get(i).getAbility().getDefenderPoint())) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankDefenderPointMemberList.get(i).getId(),
                                rankDefenderPointMemberList.get(i).getProfileUrl(),
                                rankDefenderPointMemberList.get(i).getNickname(),
                                rankDefenderPointMemberList.get(i).getPosition(),
                                rankDefenderPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankDefenderPointMemberList.get(i).getAbility().getDefenderPoint(),
                                defenderRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else {
                        defenderRank++;
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankDefenderPointMemberList.get(i).getId(),
                                rankDefenderPointMemberList.get(i).getProfileUrl(),
                                rankDefenderPointMemberList.get(i).getNickname(),
                                rankDefenderPointMemberList.get(i).getPosition(),
                                rankDefenderPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankDefenderPointMemberList.get(i).getAbility().getDefenderPoint(),
                                defenderRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }

                    if (rankerMemberResponseList.size() ==10) {
                        break;
                    }
                }
                break;

            case "goalkeeper":
                int goalkeeperRank = 1;

                List<Member> rankGoalkeeperRankPointMemberList = memberRepository.findAllByGoalkeeperPoint(ability);
                for (int i = 0; i < rankGoalkeeperRankPointMemberList.size(); i++) {

                    if (i == 0) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankGoalkeeperRankPointMemberList.get(i).getId(),
                                rankGoalkeeperRankPointMemberList.get(i).getProfileUrl(),
                                rankGoalkeeperRankPointMemberList.get(i).getNickname(),
                                rankGoalkeeperRankPointMemberList.get(i).getPosition(),
                                rankGoalkeeperRankPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankGoalkeeperRankPointMemberList.get(i).getAbility().getGoalkeeperPoint(),
                                goalkeeperRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else if (rankGoalkeeperRankPointMemberList.get(i - 1).getAbility().getGoalkeeperPoint()
                            .equals(rankGoalkeeperRankPointMemberList.get(i).getAbility().getGoalkeeperPoint())) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankGoalkeeperRankPointMemberList.get(i).getId(),
                                rankGoalkeeperRankPointMemberList.get(i).getProfileUrl(),
                                rankGoalkeeperRankPointMemberList.get(i).getNickname(),
                                rankGoalkeeperRankPointMemberList.get(i).getPosition(),
                                rankGoalkeeperRankPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankGoalkeeperRankPointMemberList.get(i).getAbility().getGoalkeeperPoint(),
                                goalkeeperRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else {
                        goalkeeperRank++;
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankGoalkeeperRankPointMemberList.get(i).getId(),
                                rankGoalkeeperRankPointMemberList.get(i).getProfileUrl(),
                                rankGoalkeeperRankPointMemberList.get(i).getNickname(),
                                rankGoalkeeperRankPointMemberList.get(i).getPosition(),
                                rankGoalkeeperRankPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankGoalkeeperRankPointMemberList.get(i).getAbility().getGoalkeeperPoint(),
                                goalkeeperRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }

                    if (rankerMemberResponseList.size() ==10) {
                        break;
                    }
                }
                break;

            case "charming":
                int charmingRank = 1;

                List<Member> rankerCharmingPointMemberList = memberRepository.findAllByCharmingPoint();
                for (int i = 0; i < rankerCharmingPointMemberList.size(); i++) {

                    if (i == 0) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerCharmingPointMemberList.get(i).getId(),
                                rankerCharmingPointMemberList.get(i).getProfileUrl(),
                                rankerCharmingPointMemberList.get(i).getNickname(),
                                rankerCharmingPointMemberList.get(i).getPosition(),
                                rankerCharmingPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankerCharmingPointMemberList.get(i).getAbility().getCharmingPoint(),
                                charmingRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else if (rankerCharmingPointMemberList.get(i - 1).getAbility().getCharmingPoint()
                            .equals(rankerCharmingPointMemberList.get(i).getAbility().getCharmingPoint())) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerCharmingPointMemberList.get(i).getId(),
                                rankerCharmingPointMemberList.get(i).getProfileUrl(),
                                rankerCharmingPointMemberList.get(i).getNickname(),
                                rankerCharmingPointMemberList.get(i).getPosition(),
                                rankerCharmingPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankerCharmingPointMemberList.get(i).getAbility().getCharmingPoint(),
                                charmingRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else {
                        charmingRank++;
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                rankerCharmingPointMemberList.get(i).getId(),
                                rankerCharmingPointMemberList.get(i).getProfileUrl(),
                                rankerCharmingPointMemberList.get(i).getNickname(),
                                rankerCharmingPointMemberList.get(i).getPosition(),
                                rankerCharmingPointMemberList.get(i).getAbility().getMvpPoint(),
                                rankerCharmingPointMemberList.get(i).getAbility().getCharmingPoint(),
                                charmingRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }

                    if (rankerMemberResponseList.size() == 10) {
                        break;
                    }
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
        List<Record> myTeamWinPointRank = recordRepository.findAllByOrderByWinPointDescWinRateDesc();

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
