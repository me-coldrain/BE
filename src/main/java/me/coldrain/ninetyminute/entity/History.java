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

    @Builder
    public History(BeforeMatching beforeMatching, AfterMatching afterMatching) {
        this.beforeMatching = beforeMatching;
        this.afterMatching = afterMatching;
    }
}
