package me.coldrain.ninetyminute.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class TeamDuplicateResponse {

    @NotNull
    private boolean exist;

    @NotNull
    private String message;

    public void updateExist(boolean exist) {
        this.exist = exist;
    }

    public void updateMessage(String message) {
        this.message = message;
    }

    @Builder
    public TeamDuplicateResponse(boolean exist, String message) {
        this.exist = exist;
        this.message = message;
    }
}
