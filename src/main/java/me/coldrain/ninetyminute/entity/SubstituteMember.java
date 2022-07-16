package me.coldrain.ninetyminute.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class SubstituteMember extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "substitute_member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "after_matching_id")
    private AfterMatching afterMatching;

    private String position;
    private Boolean anonymous;

    public void setMember (Member member) {
        this.member = member;
    }

    public void changeAnonymous (Boolean anonymous) {
        this.anonymous = anonymous;
    }

    @Builder
    public SubstituteMember (Member member, Team team, AfterMatching afterMatching, String position, Boolean anonymous) {
        this.member = member;
        this.team = team;
        this.afterMatching = afterMatching;
        this.position = position;
        this.anonymous = anonymous;
    }
}
