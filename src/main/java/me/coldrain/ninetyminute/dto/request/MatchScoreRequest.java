package me.coldrain.ninetyminute.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchScoreRequest {

    private Integer teamScore;
    private Integer opponentScore;
}
