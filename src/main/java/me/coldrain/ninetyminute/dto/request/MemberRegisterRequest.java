package me.coldrain.ninetyminute.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@ToString
public class MemberRegisterRequest {
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-z]+$")
    @NotBlank
    private String email;

    @Pattern(regexp = "^[a-zA-Z\\d]+$")
    @NotBlank
    private String password;

    @Pattern(regexp = "^[a-zA-Z\\d]+$")
    @NotBlank
    private String confirmpassword;
}
