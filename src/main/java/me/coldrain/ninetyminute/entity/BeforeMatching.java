package me.coldrain.ninetyminute.entity;


import lombok.*;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class BeforeMatching extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "before_matching_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id")
    private Apply apply;

    private Date matchDate;
    private String location;
    private String teamName;
    private String opposingTeamName;

    public String calculatedDate () {
        String date = DateFormatUtils.format(this.matchDate, "yyyyMMddHHmm");
        String matchDateInfo = "";
        if (Integer.parseInt(date.substring(8,10)) > 12) {
            int pm = Integer.parseInt(date.substring(8,10)) - 12;
            String year = date.substring(0, 4) + "년";
            String month = date.substring(4, 6) + "월";
            String day = date.substring(6, 8) + "일";
            String time = "오후 " + Integer.toString(pm) + "시" + " " + date.substring(10,12) + "분";
            matchDateInfo = year + " " + month + " " + day + " " + time;
        } else {
            String year = date.substring(0,4) + "년";
            String month = date.substring(4,6) + "월";
            String day = date.substring(6, 8) + "일";
            String time = "오전 " + date.substring(8,10) + "시" + " " + date.substring(10,12) + "분";
            matchDateInfo = year + " " + month + " " + day + " " + time;
        }
        return matchDateInfo;
    }

    @Builder
    public BeforeMatching(Apply apply, Date matchDate, String location, String teamName, String opposingTeamName) {
        this.apply = apply;
        this.matchDate = matchDate;
        this.location = location;
        this.teamName = teamName;
        this.opposingTeamName = opposingTeamName;
    }
}
