package com.example.demo.web;

import com.example.demo.sprinkle.SprinkleService;
import com.example.demo.sprinkle.repository.SprinkledMoney;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SprinkleControllerTest {
    private final SprinkleService sprinkleService = mock(SprinkleService.class);
    private final SprinkleController sprinkleController = new SprinkleController(sprinkleService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sprinkleController).build();

    @Test
    void test_sprinkleMoney_success() throws Exception {
        when(sprinkleService.sprinkle(12345L, "MOCK-ROOM-ID", 3000, 5))
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

    @Test
    void test_receive_success() throws Exception {
        when(sprinkleService.receive(12345L, "MOCK-ROOM-ID", "Thx"))
                .thenReturn(1234);

        mockMvc.perform(post("/receive")
                .header("X-USER-ID", 12345)
                .header("X-ROOM-ID", "MOCK-ROOM-ID")
                .contentType(MediaType.APPLICATION_JSON)
                //language=json
                .content("{\n" +
                        "  \"token\": \"Thx\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.received").value(1234));
    }

    @Test
    void test_check_success() throws Exception {
        when(sprinkleService.check(12345L, "Thx"))
                .thenReturn(new SprinkledMoney("Thx", "MOCK-ROOM-ID", 12345L, 3000, 3, LocalDateTime.of(2020, 12, 25, 12, 0)));

        mockMvc.perform(get("/check/{token}", "Thx")
                .header("X-USER-ID", 12345))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sprinkledTime").value("2020-12-25T12:00:00"))
                .andExpect(jsonPath("$.amount").value(3000))
                .andExpect(jsonPath("$.receivedAmount").value(0))
                .andExpect(jsonPath("$.received.length()").value(0));
    }

    @Test
    void test_check_success_divided() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        SprinkledMoney sprinkledMoney = new SprinkledMoney("Thx", "MOCK-ROOM-ID", 12345L, 3000, 3, now);
        sprinkledMoney.receive(123L);
        sprinkledMoney.receive(234L);
        sprinkledMoney.receive(345L);
        when(sprinkleService.check(12345L, "Thx"))
                .thenReturn(sprinkledMoney);

        mockMvc.perform(get("/check/{token}", "Thx")
                .header("X-USER-ID", 12345))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sprinkledTime").value(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))))
                .andExpect(jsonPath("$.amount").value(3000))
                .andExpect(jsonPath("$.receivedAmount").value(3000))
                .andExpect(jsonPath("$.received.length()").value(3))
                .andExpect(jsonPath("$.received[0].receiverId").value(123))
                .andExpect(jsonPath("$.received[0].amount").exists())
                .andExpect(jsonPath("$.received[1].receiverId").value(234))
                .andExpect(jsonPath("$.received[1].amount").exists())
                .andExpect(jsonPath("$.received[2].receiverId").value(345))
                .andExpect(jsonPath("$.received[2].amount").exists());
        ;
    }
}