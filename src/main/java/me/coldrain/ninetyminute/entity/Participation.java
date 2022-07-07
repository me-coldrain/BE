package me.coldrain.ninetyminute.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Participation extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    private Boolean approved;

    private String answer;

    @Builder
    public Participation(Member member, Team team, Boolean approved, String answer) {
        this.member = member;
        this.team = team;
        this.approved = approved;
        this.answer = answer;
    }

    public void changeApproved(Boolean status) {
        this.approved = status;
    }
}
