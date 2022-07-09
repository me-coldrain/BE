package me.coldrain.ninetyminute.controller;

import lombok.RequiredArgsConstructor;
import me.coldrain.ninetyminute.entity.Home;
import me.coldrain.ninetyminute.repository.HomeQueryRepository;
import me.coldrain.ninetyminute.repository.HomeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeQueryRepository homeQueryRepository;
    private final HomeRepository homeRepository;

    @GetMapping("/v1/home")
    public List<Home> home() {
        Home home = new Home();
        home.setWeekdays(List.of("MON", "TUE"));
        homeRepository.save(home);

        return homeQueryRepository.search();
    }

    @GetMapping("/v2/home")
    public List<List<String>> homeV2() {
        // TODO: 2022-07-09 not an entity 발생
        return homeQueryRepository.searchWeekdays();
    }

    @GetMapping("/v3/home")
    public List<List<String>> homeV3() {
        // TODO: 2022-07-09 not an entity 발생
        // TODO: 2022-07-09 결론: @ElementCollection 은 답이 없음. 
        // TODO: 2022-07-09 1:N의 N에 해당하는 weekdays 만 가지고 오고 싶어도 가지고 올 수 없다.
        return homeQueryRepository.searchWeekdaysV2();
    }


}
