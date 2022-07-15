package me.coldrain.ninetyminute.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@ToString
public class MemberNicknameDuplicateRequest {
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,8}$")
    @NotBlank
    private String nickname;
}
