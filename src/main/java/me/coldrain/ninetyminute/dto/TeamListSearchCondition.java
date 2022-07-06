package me.coldrain.ninetyminute.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamListSearchCondition {

    private String address;
    private String teamName;

    public TeamListSearchCondition(String address, String teamName) {
        this.address = address;
        this.teamName = teamName;
    }
}
