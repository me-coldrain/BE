package me.coldrain.ninetyminute.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class MemberDuplicateResponse {
    @NotNull
    private boolean exist;

    @NotNull
    private String message;
}
