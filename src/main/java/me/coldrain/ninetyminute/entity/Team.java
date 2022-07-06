package me.coldrain.ninetyminute.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"weekdays", "timeList"})
@Getter
public class Team extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;
    private String mainArea;
    private Integer point;
    private String teamProfileUrl;
    private String introduce;

    @OneToMany(mappedBy = "team")
    private List<Weekday> weekdays = new ArrayList<>(); // ['MON', 'TUE']

    @OneToMany(mappedBy = "team")
    private List<Time> timeList = new ArrayList<>();    // ['am', 'pm']

    private String preferredArea;
    private String question;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private History history;


    public void addWeekday(Weekday weekday) {
        weekdays.add(weekday);
        weekday.changeWeekday(this);
    }

    public void addTime(Time time) {
        timeList.add(time);
        time.changeTime(this);
    }
}
