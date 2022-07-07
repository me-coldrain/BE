package me.coldrain.ninetyminute.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@ToString
public class MemberRegisterRequest {
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;
}
