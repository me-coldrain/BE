package me.coldrain.ninetyminute.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@ToString
public class MemberEditRequest {
    @NotNull
    private String nickname;

    @NotNull
    private String position;

    @NotNull
    private String contact;

    private String phone;
}
