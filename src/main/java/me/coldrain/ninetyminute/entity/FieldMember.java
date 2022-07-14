package me.coldrain.ninetyminute.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class FieldMember extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_member_id")
    private Long id;

    private String position;
    private Boolean anonymous;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_matching_id")
    private BeforeMatching beforeMatching;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "after_matching_id")
    private AfterMatching afterMatching;

    public void setAfterMatching (AfterMatching afterMatching) {
        this.afterMatching = afterMatching;
    }

    @Builder
    public FieldMember (String position,Boolean anonymous, Member member, Team team, BeforeMatching beforeMatching, AfterMatching afterMatching) {
        this.position = position;
        this.anonymous = anonymous;
        this.member = member;
        this.team = team;
        this.beforeMatching = beforeMatching;
        this.afterMatching = afterMatching;
    }
}
