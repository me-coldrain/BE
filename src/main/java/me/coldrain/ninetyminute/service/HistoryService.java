package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.response.HistoryResponse;
import me.coldrain.ninetyminute.entity.History;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.repository.HistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;
    public List<HistoryResponse> searchMatchHistory(Long teamId, Member member) {
        List<HistoryResponse> historyResponseList = new ArrayList<>();
        List<History> homeHistoryList = historyRepository.findAllByHomeTeamId(teamId);
        for (History homeHistory : homeHistoryList) {
            HistoryResponse.TeamResponse team = new HistoryResponse.TeamResponse(
                    homeHistory.getBeforeMatching().getTeamName(),
                    homeHistory.getAfterMatching().getResult(),
                    homeHistory.getAfterMatching().getScore()
            );
            HistoryResponse.TeamResponse opposingTeam = new HistoryResponse.TeamResponse(
                    homeHistory.getBeforeMatching().getOpposingTeamName(),
                    homeHistory.getAfterMatching().getOpponentResult(),
                    homeHistory.getAfterMatching().getOpponentScore()
            );
            HistoryResponse historyResponse = new HistoryResponse(homeHistory.getId(), team, opposingTeam);
            historyResponseList.add(historyResponse);
        }

        List<History> awayHistoryList = historyRepository.findAllByAwayTeamId(teamId);
        for (History awayHistory : awayHistoryList) {
            HistoryResponse.TeamResponse team = new HistoryResponse.TeamResponse(
                    awayHistory.getBeforeMatching().getOpposingTeamName(),
                    awayHistory.getAfterMatching().getOpponentResult(),
                    awayHistory.getAfterMatching().getOpponentScore()
            );
            HistoryResponse.TeamResponse opposingTeam = new HistoryResponse.TeamResponse(
                    awayHistory.getBeforeMatching().getTeamName(),
                    awayHistory.getAfterMatching().getResult(),
                    awayHistory.getAfterMatching().getScore()
            );
            HistoryResponse historyResponse = new HistoryResponse(awayHistory.getId(), team, opposingTeam);
            historyResponseList.add(historyResponse);
        }
        return historyResponseList;
    }
}
