package me.coldrain.ninetyminute.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Apply extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_team_id")
    private Team applyTeam;             // 대결을 신청한 팀

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;                  // 대결을 신청 받은 팀

    @Column(name = "greeting")
    @Lob
    private String greeting;

    private Boolean approved;

    // 대결 후 대결 종료 버튼을 누를때 변경되어야 하는 사항이 있어야 한다.
    private Boolean endMatchStatus;
    private Boolean opposingTeamEndMatchStatus;

    public void changeApproved(Boolean approved) {
        this.approved = approved;
    }

    public void changeEndMatchStatus(Boolean endMatchStatus) {
        this.endMatchStatus = endMatchStatus;
    }

    public void changeOpposingTeamEndMatchStatus(Boolean opposingTeamEndMatchStatus) {
        this.opposingTeamEndMatchStatus = opposingTeamEndMatchStatus;
    }

    @Builder
    public Apply(Team applyTeam, Team team, String greeting, Boolean approved) {
        this.applyTeam = applyTeam;
        this.team = team;
        this.greeting = greeting;
        this.approved = approved;
        this.endMatchStatus = false;
        this.opposingTeamEndMatchStatus = false;
    }
}
