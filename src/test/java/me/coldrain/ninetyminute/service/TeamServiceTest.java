package me.coldrain.ninetyminute.service;

import me.coldrain.ninetyminute.dto.TeamListSearch;
import me.coldrain.ninetyminute.dto.TeamListSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamServiceTest {

    @Autowired
    TeamService teamService;

    @Test
    void 테스트() {
        TeamListSearchCondition searchCondition = new TeamListSearchCondition();
        PageRequest pageRe = PageRequest.of(0, 10);
        Page<TeamListSearch> searchPage = teamService.searchTeamList(searchCondition, pageRe);
        System.out.println(searchPage.getTotalElements());
    }
}