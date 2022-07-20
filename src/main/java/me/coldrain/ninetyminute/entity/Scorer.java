package me.coldrain.ninetyminute.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Scorer extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scorer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_member_id")
    private FieldMember fieldMember;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "substitute_member_id")
    private SubstituteMember substituteMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "after_matching_id")
    private AfterMatching afterMatching;

    private Long teamId;

    @Builder
    public Scorer (FieldMember fieldMember, SubstituteMember substituteMember, AfterMatching afterMatching, Long teamId) {
        this.fieldMember = fieldMember;
        this.substituteMember = substituteMember;
        this.afterMatching = afterMatching;
        this.teamId = teamId;
    }
}
