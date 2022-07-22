package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coldrain.ninetyminute.dto.response.MyRankResponse;
import me.coldrain.ninetyminute.dto.response.MyTeamRankResponse;
import me.coldrain.ninetyminute.dto.response.RankerMemberResponse;
import me.coldrain.ninetyminute.dto.response.RankerTeamResponse;
import me.coldrain.ninetyminute.entity.*;
import me.coldrain.ninetyminute.repository.*;
import me.coldrain.ninetyminute.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
                        rankerTeam.getTeamProfileUrl(),
                        rankerTeam.getName(),
                        rankerTeam.getRecord().getWinPoint(),
                        rank
                );
                rankerTeamResponsesList.add(rankerTeamResponse);
            } else if (rankerTeamList.get(i - 1).getWinPoint().equals(rankerTeamList.get(i).getWinPoint()) &&
                    rankerTeamList.get(i - 1).getWinRate().equals(rankerTeamList.get(i).getWinRate())) {
                RankerTeamResponse rankerTeamResponse = new RankerTeamResponse(
                        rankerTeam.getId(),
                        rankerTeam.getTeamProfileUrl(),
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
                        rankerTeam.getTeamProfileUrl(),
                        rankerTeam.getName(),
                        rankerTeam.getRecord().getWinPoint(),
                        rank
                );
                rankerTeamResponsesList.add(rankerTeamResponse);
            } else {
                rank++;
                RankerTeamResponse rankerTeamResponse = new RankerTeamResponse(
                        rankerTeam.getId(),
                        rankerTeam.getTeamProfileUrl(),
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

                    if (rankerMemberResponseList.size() == 10) {
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

                    if (rankerMemberResponseList.size() == 10) {
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

                    if (rankerMemberResponseList.size() == 10) {
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

                    if (rankerMemberResponseList.size() == 10) {
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

            default:
                int defaultMvpRank = 1;

                List<Member> defaultRankerMvpPointMemberList = memberRepository.findAllByMvpPoint();
                for (int i = 0; i < defaultRankerMvpPointMemberList.size(); i++) {

                    if (i == 0) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                defaultRankerMvpPointMemberList.get(i).getId(),
                                defaultRankerMvpPointMemberList.get(i).getProfileUrl(),
                                defaultRankerMvpPointMemberList.get(i).getNickname(),
                                defaultRankerMvpPointMemberList.get(i).getPosition(),
                                defaultRankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                defaultRankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                defaultMvpRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else if (defaultRankerMvpPointMemberList.get(i - 1).getAbility().getMvpPoint()
                            .equals(defaultRankerMvpPointMemberList.get(i).getAbility().getMvpPoint())) {
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                defaultRankerMvpPointMemberList.get(i).getId(),
                                defaultRankerMvpPointMemberList.get(i).getProfileUrl(),
                                defaultRankerMvpPointMemberList.get(i).getNickname(),
                                defaultRankerMvpPointMemberList.get(i).getPosition(),
                                defaultRankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                defaultRankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                defaultMvpRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    } else {
                        defaultMvpRank++;
                        RankerMemberResponse rankerMemberResponse = new RankerMemberResponse(
                                defaultRankerMvpPointMemberList.get(i).getId(),
                                defaultRankerMvpPointMemberList.get(i).getProfileUrl(),
                                defaultRankerMvpPointMemberList.get(i).getNickname(),
                                defaultRankerMvpPointMemberList.get(i).getPosition(),
                                defaultRankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                defaultRankerMvpPointMemberList.get(i).getAbility().getMvpPoint(),
                                defaultMvpRank
                        );
                        rankerMemberResponseList.add(rankerMemberResponse);
                    }

                    if (rankerMemberResponseList.size() == 10) {
                        break;
                    }
                }
        }
        return new ResponseEntity<>(rankerMemberResponseList, HttpStatus.OK);
    }

    //로그인 사용자 개인 랭킹
    @Transactional
    public ResponseEntity<?> myRankGet(UserDetailsImpl userDetails) {

        Member member = memberRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new NullPointerException("회원이 존재하지 않습니다."));

        if (member.getAbility() == null) {
            return new ResponseEntity<>("회원정보를 입력해주세요.", HttpStatus.BAD_REQUEST);
        }

        int myRank = 1;

        MyRankResponse myStrikerRankResponse = new MyRankResponse();
        myStrikerRankResponse.setMemberId(userDetails.getUser().getId());
        myStrikerRankResponse.setProfileImagerUrl(userDetails.getUser().getProfileUrl());
        myStrikerRankResponse.setNickname(userDetails.getUser().getNickname());
        myStrikerRankResponse.setPosition(userDetails.getUser().getPosition());
        myStrikerRankResponse.setMvpPoint(member.getAbility().getMvpPoint());

        switch (userDetails.getUser().getPosition()) {
            case "striker":
                List<Ability> myStrikerPointRank = abilityRepository.findAllByOrderByStrikerPointDesc();
                for (int i = 0; i < myStrikerPointRank.size(); i++) {
                    if (!myStrikerPointRank.get(i).getId().equals(member.getAbility().getId())) {
                        if (i == myStrikerPointRank.size() - 1) {
                            break;
                        } else if (!myStrikerPointRank.get(i).getStrikerPoint().equals(myStrikerPointRank.get(i + 1).getStrikerPoint())) {
                            myRank++;
                        }
                    } else {
                        break;
                    }
                }
                myStrikerRankResponse.setMyRank(myRank);
                myStrikerRankResponse.setPositionPoint(member.getAbility().getStrikerPoint());
                break;

            case "midfielder":
                List<Ability> myMidfielderPointRank = abilityRepository.findAllByOrderByMidfielderPointDesc();
                for (int i = 0; i < myMidfielderPointRank.size(); i++) {
                    if (!myMidfielderPointRank.get(i).getId().equals(member.getAbility().getId())) {
                        if (i == myMidfielderPointRank.size() - 1) {
                            break;
                        } else if (!myMidfielderPointRank.get(i).getMidfielderPoint().equals(myMidfielderPointRank.get(i + 1).getMidfielderPoint())) {
                            myRank++;
                        }
                    } else {
                        break;
                    }
                }
                myStrikerRankResponse.setMyRank(myRank);
                myStrikerRankResponse.setPositionPoint(member.getAbility().getMidfielderPoint());
                break;

            case "defender":
                List<Ability> myDefenderPointRank = abilityRepository.findAllByOrderByDefenderPointDesc();
                for (int i = 0; i < myDefenderPointRank.size(); i++) {
                    if (!myDefenderPointRank.get(i).getId().equals(member.getAbility().getId())) {
                        if (i == myDefenderPointRank.size() - 1) {
                            break;
                        } else if (!myDefenderPointRank.get(i).getDefenderPoint().equals(myDefenderPointRank.get(i + 1).getDefenderPoint())) {
                            myRank++;
                        }
                    } else {
                        break;
                    }
                }
                myStrikerRankResponse.setMyRank(myRank);
                myStrikerRankResponse.setPositionPoint(member.getAbility().getDefenderPoint());
                break;

            case "goalkeeper":
                List<Ability> myGoalkeeperPointRank = abilityRepository.findAllByOrderByGoalkeeperPointDesc();
                for (int i = 0; i < myGoalkeeperPointRank.size(); i++) {
                    if (!myGoalkeeperPointRank.get(i).getId().equals(member.getAbility().getId())) {
                        if (i == myGoalkeeperPointRank.size() - 1) {
                            break;
                        } else if (!myGoalkeeperPointRank.get(i).getGoalkeeperPoint().equals(myGoalkeeperPointRank.get(i + 1).getGoalkeeperPoint())) {
                            myRank++;
                        }
                    } else {
                        break;
                    }
                }
                myStrikerRankResponse.setMyRank(myRank);
                myStrikerRankResponse.setPositionPoint(member.getAbility().getGoalkeeperPoint());
                break;
        }
        return new ResponseEntity<>(myStrikerRankResponse, HttpStatus.OK);
    }

    //로그인 사용자가 참여하고 있는 팀의 랭킹 조회
    public ResponseEntity<?> myTeamRankGet(UserDetailsImpl userDetails) {
        MyTeamRankResponse myTeamRankResponse = new MyTeamRankResponse();
        MyTeamRankResponse.teamCaptain teamCaptainRank = new MyTeamRankResponse.teamCaptain();
        MyTeamRankResponse.teamMember teamMemberRank = new MyTeamRankResponse.teamMember();

        List<Record> TeamWinPointRank = recordRepository.findAllByOrderByWinPointDescWinRateDesc();

        Member member = memberRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 사용자 입니다."));

        int myOpenTeamRank = 1;

        if (member.getOpenTeam() != null) {
            teamCaptainRank.setTeamId(member.getOpenTeam().getId());
            teamCaptainRank.setMainArea(member.getOpenTeam().getMainArea());
            teamCaptainRank.setTeamName(member.getOpenTeam().getName());
            teamCaptainRank.setWinPoint(member.getOpenTeam().getRecord().getWinPoint());

            for (int i = 0; i < TeamWinPointRank.size(); i++) {
                if (!TeamWinPointRank.get(i).getId().equals(member.getOpenTeam().getRecord().getId())) {
                    if (i == TeamWinPointRank.size() - 1) {
                        break;
                    } else if (!TeamWinPointRank.get(i).getWinPoint().equals(TeamWinPointRank.get(i + 1).getWinPoint())) {
                        myOpenTeamRank++;
                    } else if (TeamWinPointRank.get(i).getWinPoint().equals(TeamWinPointRank.get(i + 1).getWinPoint()) &&
                            !TeamWinPointRank.get(i).getWinRate().equals(TeamWinPointRank.get(i + 1).getWinRate())) {
                        myOpenTeamRank++;
                    }
                } else {
                    break;
                }
            }
            teamCaptainRank.setMyOpenTeamRank(myOpenTeamRank);
        } else {
            teamCaptainRank = null;
        }

        int myTeamRank = 1;
        List<MyTeamRankResponse.teamMember> teamMemberRankList = new ArrayList<>();
        List<Participation> myTeam = participationRepository.findAllByMemberIdTrue(member.getId());
        for (int i = 0; i < myTeam.size(); i++) {
            if (!member.getOpenTeam().getId().equals(myTeam.get(i).getTeam().getId())) {
                teamMemberRank.setTeamId(myTeam.get(i).getTeam().getId());
                teamMemberRank.setMainArea(myTeam.get(i).getTeam().getMainArea());
                teamMemberRank.setTeamName(myTeam.get(i).getTeam().getName());
                teamMemberRank.setWinPoint(myTeam.get(i).getTeam().getRecord().getWinPoint());

                for (int j = 0; j < TeamWinPointRank.size(); j++) {
                    if (!TeamWinPointRank.get(j).getId().equals(myTeam.get(i).getTeam().getRecord().getId())) {
                        if (i == TeamWinPointRank.size() - 1) {
                            break;
                        } else if (!TeamWinPointRank.get(j).getWinPoint().equals(TeamWinPointRank.get(j + 1).getWinPoint())) {
                            myTeamRank++;
                        } else if (TeamWinPointRank.get(j).getWinPoint().equals(TeamWinPointRank.get(j + 1).getWinPoint()) &&
                                !TeamWinPointRank.get(j).getWinRate().equals(TeamWinPointRank.get(j + 1).getWinRate())) {
                            myTeamRank++;
                        }
                    } else {
                        break;
                    }
                }
                teamMemberRank.setMyTeamRank(myTeamRank);
                teamMemberRankList.add(teamMemberRank);
            }
        }

        myTeamRankResponse.setTeamCaptain(teamCaptainRank);
        myTeamRankResponse.setTeamMember(teamMemberRankList);
        return new ResponseEntity<>(myTeamRankResponse, HttpStatus.OK);
    }
}
