package me.coldrain.ninetyminute.dto.request;

import lombok.*;
import me.coldrain.ninetyminute.entity.Time;
import me.coldrain.ninetyminute.entity.Weekday;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class TeamRegisterRequest {

    private String teamName;

    private MultipartFile teamImageFile;

    private String introduce;

    private String mainArea;

    private List<String> weekday;

    private List<String> time;

    private String preferredArea;

    private String question;
}
