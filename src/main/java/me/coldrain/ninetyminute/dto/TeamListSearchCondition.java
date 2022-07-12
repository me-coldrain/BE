package me.coldrain.ninetyminute.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamListSearchCondition {

    private String address;
    private String teamName;

    private List<String> weekdays;

    private List<String> time;

    private String winRate;

    private Boolean recruit;

    private Boolean match;

    public TeamListSearchCondition(String address, String teamName, List<String> weekdays, List<String> time, String winRate, Boolean recruit, Boolean match) {
        this.address = address;
        this.teamName = teamName;
        this.weekdays = weekdays;
        this.time = time;
        this.winRate = winRate;
        this.recruit = recruit;
        this.match = match;
    }
}
