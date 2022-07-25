package me.coldrain.ninetyminute.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamListSearchCondition {

    private String address;
    private String input;

    private List<String> weekdays;

    private List<String> time;

    private String winRate;

    private Boolean recruit;

    private Boolean match;

    public TeamListSearchCondition(String address, String input, List<String> weekdays, List<String> time, String winRate, Boolean recruit, Boolean match) {
        this.address = address;
        this.input = input;
        this.weekdays = weekdays;
        this.time = time;
        this.winRate = winRate;
        this.recruit = recruit;
        this.match = match;
    }
}
