package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.response.HistoryDetailResponse;
import me.coldrain.ninetyminute.dto.response.HistoryResponse;
import me.coldrain.ninetyminute.entity.*;
import me.coldrain.ninetyminute.repository.FieldMemberRepository;
import me.coldrain.ninetyminute.repository.HistoryRepository;
import me.coldrain.ninetyminute.repository.ScorerRepository;
import me.coldrain.ninetyminute.repository.SubstituteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final ScorerRepository scorerRepository;
    private final FieldMemberRepository fieldMemberRepository;
    private final SubstituteRepository substituteRepository;

    public List<HistoryResponse> searchMatchHistory(Long teamId, Member member) {
        List<HistoryResponse> historyResponseList = new ArrayList<>();
        List<History> historyList = historyRepository.findAllByTeamId(teamId);
        for (History history : historyList) {
            if (teamId.equals(history.getTeam().getId())) {
                HistoryResponse.TeamResponse team = new HistoryResponse.TeamResponse(
                        history.getTeam().getName(),
                        history.getAfterMatching().getResult(),
                        history.getAfterMatching().getScore()
                );
                HistoryResponse.TeamResponse opposingTeam = new HistoryResponse.TeamResponse(
                        history.getOpposingTeam().getName(),
                        history.getAfterMatching().getOpponentResult(),
                        history.getAfterMatching().getOpponentScore()
                );
                HistoryResponse historyResponse = new HistoryResponse(history.getId(), history.getBeforeMatching().calculatedDate(), team, opposingTeam);
                historyResponseList.add(historyResponse);
            } else {
                HistoryResponse.TeamResponse team = new HistoryResponse.TeamResponse(
                        history.getOpposingTeam().getName(),
                        history.getAfterMatching().getOpponentResult(),
                        history.getAfterMatching().getOpponentScore()
                );
                HistoryResponse.TeamResponse opposingTeam = new HistoryResponse.TeamResponse(
                        history.getTeam().getName(),
                        history.getAfterMatching().getResult(),
                        history.getAfterMatching().getScore()
                );
                HistoryResponse historyResponse = new HistoryResponse(history.getId(), history.getBeforeMatching().calculatedDate(), team, opposingTeam);
                historyResponseList.add(historyResponse);
            }
        }
        return historyResponseList;
    }

    public HistoryDetailResponse searchMatchHistoryDetail(Long teamId, Long historyId, Member member) {
        History history = historyRepository.findById(historyId).orElseThrow(() -> new IllegalArgumentException("히스토리가 존재하지 않습니다."));
        List<String> homeScorers = new ArrayList<>();
        List<String> awayScorers = new ArrayList<>();
        HistoryDetailResponse historyDetailResponse = new HistoryDetailResponse(history.getId(), history.getBeforeMatching().calculatedDate(), null, null);

        HistoryDetailResponse.TeamResponse.PlayerResponse homeFieldPlayerResponse = new HistoryDetailResponse.TeamResponse.PlayerResponse(null, null, null, null);
        HistoryDetailResponse.TeamResponse.PlayerResponse homeSubPlayerResponse = new HistoryDetailResponse.TeamResponse.PlayerResponse(null, null, null, null);
        HistoryDetailResponse.TeamResponse.PlayerResponse awayFieldPlayerResponse = new HistoryDetailResponse.TeamResponse.PlayerResponse(null, null, null, null);
        HistoryDetailResponse.TeamResponse.PlayerResponse awaySubPlayerResponse = new HistoryDetailResponse.TeamResponse.PlayerResponse(null, null, null, null);

        HistoryDetailResponse.TeamResponse.MemberResponse homeFieldMemberResponse = new HistoryDetailResponse.TeamResponse.MemberResponse(null, null, null, null);
        HistoryDetailResponse.TeamResponse.MemberResponse homeSubMemberResponse = new HistoryDetailResponse.TeamResponse.MemberResponse(null, null, null, null);
        HistoryDetailResponse.TeamResponse.MemberResponse awayFieldMemberResponse = new HistoryDetailResponse.TeamResponse.MemberResponse(null, null, null, null);
        HistoryDetailResponse.TeamResponse.MemberResponse awaySubMemberResponse = new HistoryDetailResponse.TeamResponse.MemberResponse(null, null, null, null);

        if (teamId.equals(history.getBeforeMatching().getApply().getTeam().getId())) {
            // home team
            List<FieldMember> homeTeamFieldMembers = fieldMemberRepository.findAllByAfterMatchingFieldMembers(teamId, history.getAfterMatching().getId());
            List<SubstituteMember> homeTeamSubMembers = substituteRepository.findAllByAllMatchSubstituteMembers(teamId, history.getAfterMatching().getId());
            List<Scorer> homeTeamScorerList = scorerRepository.findAllByAfterMatchingIdAndTeamId(history.getAfterMatching().getId(), teamId);
            HistoryDetailResponse.TeamResponse homeTeamResponse = HistoryDetailResponse.TeamResponse.builder()
                    .mvp(history.getAfterMatching().getMvpNickname())
                    .teamName(history.getBeforeMatching().getTeamName())
                    .score(history.getAfterMatching().getScore())
                    .record(history.getAfterMatching().getResult())
                    .scorer(null)
                    .fieldMembers(null)
                    .substituteMembers(null)
                    .build();
            homeScorers = homeTeamResponse.updateScorer(homeTeamScorerList, homeScorers);
            homeTeamResponse.setScorer(homeScorers);
            homeTeamResponse.setFieldMembers(homeTeamFieldMembers, homeFieldMemberResponse, homeFieldPlayerResponse);
            homeTeamResponse.setSubstituteMembers(homeTeamSubMembers, homeSubMemberResponse, homeSubPlayerResponse);

            // away team
            List<FieldMember> awayTeamFieldMembers = fieldMemberRepository.findAllByAfterMatchingFieldMembers(history.getBeforeMatching().getApply().getApplyTeam().getId(), history.getAfterMatching().getId());
            List<SubstituteMember> awayTeamSubMembers = substituteRepository.findAllByAllMatchSubstituteMembers(history.getBeforeMatching().getApply().getApplyTeam().getId(), history.getAfterMatching().getId());
            List<Scorer> awayTeamScorerList = scorerRepository.findAllByAfterMatchingIdAndTeamId(history.getAfterMatching().getId(), history.getBeforeMatching().getApply().getApplyTeam().getId());
            HistoryDetailResponse.TeamResponse awayTeamResponse = HistoryDetailResponse.TeamResponse.builder()
                    .mvp(history.getAfterMatching().getOpponentMvpNickname())
                    .teamName(history.getBeforeMatching().getOpposingTeamName())
                    .score(history.getAfterMatching().getOpponentScore())
                    .record(history.getAfterMatching().getOpponentResult())
                    .scorer(null)
                    .fieldMembers(null)
                    .substituteMembers(null)
                    .build();
            awayScorers = awayTeamResponse.updateScorer(awayTeamScorerList, awayScorers);
            awayTeamResponse.setScorer(awayScorers);
            awayTeamResponse.setFieldMembers(awayTeamFieldMembers, awayFieldMemberResponse, awayFieldPlayerResponse);
            awayTeamResponse.setSubstituteMembers(awayTeamSubMembers, awaySubMemberResponse, awaySubPlayerResponse);

            historyDetailResponse.addTeams(homeTeamResponse, awayTeamResponse);
        } else if (teamId.equals(history.getBeforeMatching().getApply().getApplyTeam().getId())) {
            // home team
            List<FieldMember> homeTeamFieldMembers = fieldMemberRepository.findAllByAfterMatchingFieldMembers(teamId, history.getAfterMatching().getId());
            List<SubstituteMember> homeTeamSubMembers = substituteRepository.findAllByAllMatchSubstituteMembers(teamId, history.getAfterMatching().getId());
            List<Scorer> homeTeamScorerList = scorerRepository.findAllByAfterMatchingIdAndTeamId(history.getAfterMatching().getId(), teamId);
            HistoryDetailResponse.TeamResponse homeTeamResponse = HistoryDetailResponse.TeamResponse.builder()
                    .mvp(history.getAfterMatching().getOpponentMvpNickname())
                    .teamName(history.getBeforeMatching().getOpposingTeamName())
                    .score(history.getAfterMatching().getOpponentScore())
                    .record(history.getAfterMatching().getOpponentResult())
                    .scorer(null)
                    .fieldMembers(null)
                    .substituteMembers(null)
                    .build();
            homeScorers = homeTeamResponse.updateScorer(homeTeamScorerList, homeScorers);
            homeTeamResponse.setScorer(homeScorers);
            homeTeamResponse.setFieldMembers(homeTeamFieldMembers, homeFieldMemberResponse, homeFieldPlayerResponse);
            homeTeamResponse.setSubstituteMembers(homeTeamSubMembers, homeSubMemberResponse, homeSubPlayerResponse);

            //away team
            List<FieldMember> awayTeamFieldMembers = fieldMemberRepository.findAllByAfterMatchingFieldMembers(history.getBeforeMatching().getApply().getTeam().getId(), history.getAfterMatching().getId());
            List<SubstituteMember> awayTeamSubMembers = substituteRepository.findAllByAllMatchSubstituteMembers(history.getBeforeMatching().getApply().getTeam().getId(), history.getAfterMatching().getId());
            List<Scorer> awayTeamScorerList = scorerRepository.findAllByAfterMatchingIdAndTeamId(history.getAfterMatching().getId(), history.getBeforeMatching().getApply().getTeam().getId());
            HistoryDetailResponse.TeamResponse awayTeamResponse = HistoryDetailResponse.TeamResponse.builder()
                    .mvp(history.getAfterMatching().getMvpNickname())
                    .teamName(history.getBeforeMatching().getTeamName())
                    .score(history.getAfterMatching().getScore())
                    .record(history.getAfterMatching().getResult())
                    .scorer(null)
                    .fieldMembers(null)
                    .substituteMembers(null)
                    .build();
            awayScorers = awayTeamResponse.updateScorer(awayTeamScorerList, awayScorers);
            awayTeamResponse.setScorer(awayScorers);
            awayTeamResponse.setFieldMembers(awayTeamFieldMembers, awayFieldMemberResponse, awayFieldPlayerResponse);
            awayTeamResponse.setSubstituteMembers(awayTeamSubMembers, awaySubMemberResponse, awaySubPlayerResponse);

            historyDetailResponse.addTeams(homeTeamResponse, awayTeamResponse);
        } else throw new IllegalArgumentException("해당 팀의 경기 히스토리가 아닙니다.");
        return historyDetailResponse;
    }
}
