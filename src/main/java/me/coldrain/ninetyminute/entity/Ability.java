package me.coldrain.ninetyminute.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
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

    public Ability() {
        this.strikerPoint = 0;
        this.midfielderPoint = 0;
        this.defenderPoint = 0;
        this.goalkeeperPoint = 0;
        this.mvpPoint = 0;
        this.charmingPoint = 0;
    }

    public void updateStrikePoint() {
        this.strikerPoint += 1;
    }

    public void updateMidfielderPoint() {
        this.midfielderPoint += 1;
    }

    public void updateDefenderPoint() {
        this.defenderPoint += 1;
    }

    public void updateGoalkeeperPoint() {
        this.goalkeeperPoint += 1;
    }

    public void updateMVPPoint() {
        this.mvpPoint += 1;
    }

    public void updateCharmingPoint() {
        this.charmingPoint += 1;
    }

    @Builder
    public Ability(Integer strikerPoint, Integer midfielderPoint, Integer defenderPoint, Integer goalkeeperPoint, Integer mvpPoint, Integer charmingPoint) {
        this.strikerPoint = strikerPoint;
        this.midfielderPoint = midfielderPoint;
        this.defenderPoint = defenderPoint;
        this.goalkeeperPoint = goalkeeperPoint;
        this.mvpPoint = mvpPoint;
        this.charmingPoint = charmingPoint;
    }
}
