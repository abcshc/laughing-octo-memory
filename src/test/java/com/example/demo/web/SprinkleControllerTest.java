package com.example.demo.web;

import com.example.demo.sprinkle.SprinkleService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SprinkleControllerTest {
    private final SprinkleService sprinkleService = mock(SprinkleService.class);
    private final SprinkleController sprinkleController = new SprinkleController(sprinkleService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sprinkleController).build();

    @Test
    void test_sprinkleMoney_success() throws Exception {
        when(sprinkleService.sprinkleMoney(12345L, "MOCK-ROOM-ID", 3000, 5))
                .thenReturn("Thx");

        mockMvc.perform(post("/sprinkle")
                .header("X-USER-ID", 12345)
                .header("X-ROOM-ID", "MOCK-ROOM-ID")
                .contentType(MediaType.APPLICATION_JSON)
                //language=json
                .content("{\n" +
                        "  \"amount\": 3000,\n" +
                        "  \"divided\": 5\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("Thx"));
    }
}