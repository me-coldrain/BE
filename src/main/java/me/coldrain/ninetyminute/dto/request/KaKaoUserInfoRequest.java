package me.coldrain.ninetyminute.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KaKaoUserInfoRequest {
    private Long id;
    private String email;
}
