package me.coldrain.ninetyminute.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class History extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "after_matching_id")
    private AfterMatching afterMatching;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_matching_id")
    private BeforeMatching beforeMatching;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opposing_team_id")
    private Team opposingTeam;

    @Builder
    public History(BeforeMatching beforeMatching, AfterMatching afterMatching, Team team, Team opposingTeam) {
        this.beforeMatching = beforeMatching;
        this.afterMatching = afterMatching;
        this.team = team;
        this.opposingTeam = opposingTeam;
    }
}
