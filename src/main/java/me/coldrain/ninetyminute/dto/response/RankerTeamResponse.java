package me.coldrain.ninetyminute.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RankerTeamResponse {
    private Long teamId;
    private String mainArea;
    private String teamName;
    private int winPoint;
}
