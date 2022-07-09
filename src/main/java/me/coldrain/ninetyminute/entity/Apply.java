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

    private Boolean approved;

    public void changeApproved(Boolean approved) {
        this.approved = approved;
    }

    @Builder
    public Apply(Team applyTeam, Team team, Boolean approved) {
        this.applyTeam = applyTeam;
        this.team = team;
        this.approved = approved;
    }
}
