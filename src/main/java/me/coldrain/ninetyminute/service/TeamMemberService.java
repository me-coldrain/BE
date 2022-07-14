package me.coldrain.ninetyminute.service;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.dto.response.TeamMemberOfferResponse;
import me.coldrain.ninetyminute.dto.response.TeamMemberResponse;
import me.coldrain.ninetyminute.entity.Member;
import me.coldrain.ninetyminute.entity.Participation;
import me.coldrain.ninetyminute.entity.Team;
import me.coldrain.ninetyminute.repository.MemberRepository;
import me.coldrain.ninetyminute.repository.ParticipationRepository;
import me.coldrain.ninetyminute.repository.TeamRepository;
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
    private final TeamRepository teamRepository;

    //팀 멤버 조회
    public ResponseEntity<?> teamMemberGet(Long teamId, Long member_id) {
        TeamMemberResponse teamMemberResponse = new TeamMemberResponse();

        Member captain = memberRepository.findByOpenTeam(teamId).orElseThrow();
        teamMemberResponse.setTeamCaptain(captain.getId().equals(member_id));

        TeamMemberResponse.CaptainMember CaptainMember = new TeamMemberResponse.CaptainMember(
                captain.getId(),
                captain.getProfileUrl(),
                captain.getNickname(),
                captain.getAbility().getMvpPoint(),
                captain.getPosition(),
                captain.getAbility().getStrikerPoint(),
                captain.getAbility().getMidfielderPoint(),
                captain.getAbility().getDefenderPoint(),
                captain.getAbility().getGoalkeeperPoint()

        );
        teamMemberResponse.setCaptain(CaptainMember);

        List<Participation> teamMemberList = participationRepository.findAllByTeamIdTrue(teamId);
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

    public ResponseEntity<?> teamMemberOfferGet(Long teamId) {
        TeamMemberOfferResponse teamMemberOfferResponse = new TeamMemberOfferResponse();

        Team teamOffer = teamRepository.findById(teamId).orElseThrow();
        teamMemberOfferResponse.setQuestion(teamOffer.getQuestion());

        List<TeamMemberOfferResponse.OfferedMember> OfferedMemberList = new ArrayList<>();
        List<Participation> teamMemberOffer = participationRepository.findAllByTeamIdFalse(teamId);
        for(int i=0; i<teamMemberOffer.size(); i++) {
            TeamMemberOfferResponse.OfferedMember OfferedMember = new TeamMemberOfferResponse.OfferedMember(
                    teamMemberOffer.get(i).getMember().getId(),
                    teamMemberOffer.get(i).getMember().getProfileUrl(),
                    teamMemberOffer.get(i).getMember().getNickname(),
                    teamMemberOffer.get(i).getMember().getAbility().getMvpPoint(),
                    teamMemberOffer.get(i).getMember().getPosition(),
                    teamMemberOffer.get(i).getMember().getAbility().getStrikerPoint(),
                    teamMemberOffer.get(i).getMember().getAbility().getMidfielderPoint(),
                    teamMemberOffer.get(i).getMember().getAbility().getDefenderPoint(),
                    teamMemberOffer.get(i).getMember().getAbility().getGoalkeeperPoint(),
                    teamMemberOffer.get(i).getAnswer()
            );
            OfferedMemberList.add(OfferedMember);
        }
        teamMemberOfferResponse.setOfferedMembers(OfferedMemberList);

        return new ResponseEntity<>(teamMemberOfferResponse, HttpStatus.OK);
    }
}
