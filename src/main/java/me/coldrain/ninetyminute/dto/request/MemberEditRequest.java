package me.coldrain.ninetyminute.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class MemberEditRequest {

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,8}$")
    @NotBlank
    private String nickname;

    @NotBlank
    private String position;

    @NotBlank
    private String contact;

    @Pattern(regexp = "^\\d+$")
    private String phone;
}
