package me.coldrain.ninetyminute.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecruitStartRequest {

    private String question;

    public RecruitStartRequest(String question) {
        this.question = question;
    }
}
