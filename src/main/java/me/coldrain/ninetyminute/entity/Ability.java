package me.coldrain.ninetyminute.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Ability extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ability_id")
    private Long id;

    private Integer strikerPoint;
    private Integer midfielderPoint;
    private Integer defenderPoint;
    private Integer goalkeeperPoint;
    private Integer mvpPoint;
    private Integer charmingPoint;
}
