package me.coldrain.ninetyminute.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class JwtTokenResponse {
//    private int code = HttpStatus.OK.value();
    private String accesstoken;
    private boolean first = false;
//    private String refreshtoken;

//    private String message;
//    private HttpStatus errorststus;
}
