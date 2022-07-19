package me.coldrain.ninetyminute.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MyRankResponse {
    private Long memberId;
    private String profileImagerUrl;
    private String nickname;
    private String position;
    private int mvpPoint;
    private int positionPoint;
    private int myRank;
}
