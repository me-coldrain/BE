package me.coldrain.ninetyminute.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@ToString
public class MemberEditRequest {
    //영어,한글을 포함해 2~8자리이어야 합니다
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,8}$")
    @NotBlank
    private String nickname;

    @NotBlank
    private String position;

    @NotBlank
    private String contact;
    //숫자만 입력 가능합니다
    @Pattern(regexp = "^\\d+$")
    private String phone;
}
