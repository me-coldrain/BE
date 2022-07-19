package me.coldrain.ninetyminute.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
public class Record extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    private Integer totalGameCount;
    private Integer winCount;
    private Integer loseCount;
    private Integer drawCount;
    private Double winRate;
    private Integer winPoint;

    public Record() {
        this.totalGameCount = 0;
        this.winCount = 0;
        this.loseCount = 0;
        this.drawCount = 0;
        this.winRate = 0.0;
        this.winPoint = 0;
    }

    public void updateTotalGameCount() {
        this.totalGameCount += 1;
    }

    public void updateWinCount() {
        this.winCount += 1;
        this.winPoint += 3;
    }

    public void updateLoseCount() {
        this.loseCount += 1;
        if (this.winPoint > 0) {
            this.winPoint -= 1;
        }
    }

    public void updateDrawCount() {
        this.drawCount += 1;
    }

    public void updateWinRate(Double winRate) {
        this.winRate = winRate;
    }

    public void updateWinPoint(Integer winPoint) {
        this.winPoint = winPoint;
    }

    @Builder
    public Record(Integer totalGameCount, Integer winCount, Integer loseCount, Integer drawCount, Double winRate, Integer winPoint) {
        this.totalGameCount = totalGameCount;
        this.winCount = winCount;
        this.loseCount = loseCount;
        this.drawCount = drawCount;
        this.winRate = winRate;
        this.winPoint = winPoint;
    }
}
