package me.coldrain.ninetyminute.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class AfterMatching extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "after_matching_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_matching_id")
    private BeforeMatching beforeMatching;

    private String mvpNickname;
    private String moodMaker;
    private String opponentMvpNickname;
    private String opponentMoodMaker;
    private Integer score;
    private Integer opponentScore;
    private String result;
    private String opponentResult;
    private Boolean admitStatus;    // true : 점수 인정/ false : 점수 불인정 -> 정정 요청

    public void correctScore(Integer score, Integer opponentScore) {
        this.score = score;
        this.opponentScore = opponentScore;
    }

    public void editMVPNickname(String mvpNickname) {
        this.mvpNickname = mvpNickname;
    }

    public void editMoodMaker(String moodMaker) {
        this.moodMaker = moodMaker;
    }

    public void editOpponentMVPNickname(String opponentMvpNickname) { this.opponentMvpNickname = opponentMvpNickname; }

    public void editOpponentMoodMaker(String opponentMoodMaker) {
        this.opponentMoodMaker = opponentMoodMaker;
    }

    public void editResult(String result, String opponentResult) {
        this.result = result;
        this.opponentResult = opponentResult;
    }

    public void changeAdmitStatus(Boolean admitStatus) {
        this.admitStatus = admitStatus;
    }

    @Builder
    public AfterMatching(BeforeMatching beforeMatching, String mvpNickname, String moodMaker, String opponentMvpNickname, String opponentMoodMaker, Integer score, Integer opponentScore, String result, String opponentResult, Boolean admitStatus) {
        this.beforeMatching = beforeMatching;
        this.mvpNickname = mvpNickname;
        this.moodMaker = moodMaker;
        this.opponentMvpNickname = opponentMvpNickname;
        this.opponentMoodMaker = opponentMoodMaker;
        this.score = score;
        this.opponentScore = opponentScore;
        this.result = result;
        this.opponentResult = opponentResult;
        this.admitStatus = admitStatus;
    }
}
