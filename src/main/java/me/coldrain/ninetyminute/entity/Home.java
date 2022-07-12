package me.coldrain.ninetyminute.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Home {

    @Id @GeneratedValue
    private Long id;

    @ElementCollection
    private List<String> weekdays = new ArrayList<>();

    public Home(List<String> weekdays) {
        this.weekdays = weekdays;
    }
}
