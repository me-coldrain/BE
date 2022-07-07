package me.coldrain.ninetyminute.controller;

import me.coldrain.ninetyminute.service.TeamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * author: coldrain-f
 */
@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TeamService teamService;

    @Test
    @DisplayName("mockMvc 의존성 주입 테스트")
    void mockMvc_의존성_주입_테스트() {
        assertNotNull(mockMvc);
    }

    @Test
    @DisplayName("팀 등록 테스트")
    void 팀_등록_테스트() throws Exception {
        ClassPathResource resource = new ClassPathResource("images/dummy.jpg");
        MockMultipartFile teamImageFile = new MockMultipartFile(
                "dummy", resource.getFilename(),
                MediaType.IMAGE_PNG_VALUE, resource.getInputStream()
        );

        mockMvc.perform(multipart("/api/home/teams")
                        .file("teamImageFile", teamImageFile.getBytes())
                        .param("teamName", "teamName")
                        .param("introduce", "introduce")
                        .param("mainArea", "mainArea")
                        .param("weekDay", "MON", "TUE")
                        .param("time", "am", "pm")
                        .param("preferredArea", "home")
                        .param("question", "question")
                )
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
}