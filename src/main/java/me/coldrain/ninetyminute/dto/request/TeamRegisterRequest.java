package me.coldrain.ninetyminute.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class TeamRegisterRequest {

    private String url;

    private String teamName;

    private String introduce;

    private String mainArea;

    private List<String> weekday;

    private List<String> time;

    private String preferredArea;
}
