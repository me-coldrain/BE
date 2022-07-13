package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.response.TeamMemberResponse;
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
public class TeamMemberService {
    private final ParticipationRepository participationRepository;
    private final MemberRepository memberRepository;

    public ResponseEntity<?> teamMemberGet(Long team_id, Long member_id) {
        TeamMemberResponse teamMemberResponse = new TeamMemberResponse();

        Member captain = memberRepository.findByOpenTeam(team_id).orElseThrow();
        teamMemberResponse.setTeamCaptain(captain.getId().equals(member_id));

        String captainPosition = captain.getPosition();
        switch (captainPosition) {
            case "striker":
                TeamMemberResponse.CaptainMember strikerCaptainMember = new TeamMemberResponse.CaptainMember(
                        captain.getId(),
                        captain.getProfileUrl(),
                        captain.getNickname(),
                        captain.getAbility().getMvpPoint(),
                        captain.getPosition(),
                        captain.getAbility().getStrikerPoint()
                );
                teamMemberResponse.setCaptain(strikerCaptainMember);
                break;

            case "midfielder":
                TeamMemberResponse.CaptainMember midfielderCaptainMember = new TeamMemberResponse.CaptainMember(
                        captain.getId(),
                        captain.getProfileUrl(),
                        captain.getNickname(),
                        captain.getAbility().getMvpPoint(),
                        captain.getPosition(),
                        captain.getAbility().getMidfielderPoint()
                );
                teamMemberResponse.setCaptain(midfielderCaptainMember);
                break;

            case "defender":
                TeamMemberResponse.CaptainMember defenderCaptainMember = new TeamMemberResponse.CaptainMember(
                        captain.getId(),
                        captain.getProfileUrl(),
                        captain.getNickname(),
                        captain.getAbility().getMvpPoint(),
                        captain.getPosition(),
                        captain.getAbility().getDefenderPoint()
                );
                teamMemberResponse.setCaptain(defenderCaptainMember);
                break;

            case "goalkeeper":
                TeamMemberResponse.CaptainMember goalkeeperCaptainMember = new TeamMemberResponse.CaptainMember(
                        captain.getId(),
                        captain.getProfileUrl(),
                        captain.getNickname(),
                        captain.getAbility().getMvpPoint(),
                        captain.getPosition(),
                        captain.getAbility().getGoalkeeperPoint()
                );
                teamMemberResponse.setCaptain(goalkeeperCaptainMember);
                break;
        }

        List<Participation> teamMemberList = participationRepository.findAllByTeamId(team_id);

        List<TeamMemberResponse.StrikerMember> strikerMemberList = new ArrayList<>();
        List<TeamMemberResponse.MidfielderMember> midfielderMemberList = new ArrayList<>();
        List<TeamMemberResponse.DefenderMember> defenderMemberList = new ArrayList<>();
        List<TeamMemberResponse.GoalkeeperMember> goalkeeperMemberList = new ArrayList<>();

        for (Participation participation : teamMemberList) {
            String position = participation.getMember().getPosition();

            switch (position) {
                case "striker":
                    TeamMemberResponse.StrikerMember strikerMember = new TeamMemberResponse.StrikerMember(
                            participation.getMember().getId(),
                            participation.getMember().getProfileUrl(),
                            participation.getMember().getNickname(),
                            participation.getMember().getAbility().getMvpPoint(),
                            participation.getMember().getAbility().getStrikerPoint()
                    );
                    strikerMemberList.add(strikerMember);
                    break;

                case "midfielder":
                    TeamMemberResponse.MidfielderMember midfielderMember = new TeamMemberResponse.MidfielderMember(
                            participation.getMember().getId(),
                            participation.getMember().getProfileUrl(),
                            participation.getMember().getNickname(),
                            participation.getMember().getAbility().getMvpPoint(),
                            participation.getMember().getAbility().getMidfielderPoint()
                    );
                    midfielderMemberList.add(midfielderMember);
                    break;

                case "defender":
                    TeamMemberResponse.DefenderMember defenderMember = new TeamMemberResponse.DefenderMember(
                            participation.getMember().getId(),
                            participation.getMember().getProfileUrl(),
                            participation.getMember().getNickname(),
                            participation.getMember().getAbility().getMvpPoint(),
                            participation.getMember().getAbility().getDefenderPoint()
                    );
                    defenderMemberList.add(defenderMember);
                    break;

                case "goalkeeper":
                    TeamMemberResponse.GoalkeeperMember goalkeeperMember = new TeamMemberResponse.GoalkeeperMember(
                            participation.getMember().getId(),
                            participation.getMember().getProfileUrl(),
                            participation.getMember().getNickname(),
                            participation.getMember().getAbility().getMvpPoint(),
                            participation.getMember().getAbility().getGoalkeeperPoint()
                    );
                    goalkeeperMemberList.add(goalkeeperMember);
                    break;
            }
        }
        teamMemberResponse.setStriker(strikerMemberList);
        teamMemberResponse.setMidfielder(midfielderMemberList);
        teamMemberResponse.setDefender(defenderMemberList);
        teamMemberResponse.setGoalkeeper(goalkeeperMemberList);

        return new ResponseEntity<>(teamMemberResponse, HttpStatus.OK);
    }
}
