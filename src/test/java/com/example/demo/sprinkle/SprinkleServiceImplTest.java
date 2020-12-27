package com.example.demo.sprinkle;

import com.example.demo.sprinkle.repository.DividedMoney;
import com.example.demo.sprinkle.repository.SprinkledMoney;
import com.example.demo.sprinkle.repository.SprinkledMoneyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

class SprinkleServiceImplTest {
    private final SprinkledMoneyRepository sprinkledMoneyRepository = mock(SprinkledMoneyRepository.class);
    private final TokenGenerator tokenGenerator = mock(TokenGenerator.class);
    private final SprinkleService sprinkleService = new SprinkleServiceImpl(sprinkledMoneyRepository, tokenGenerator);

    @AfterEach
    void tearDown() {
        reset(sprinkledMoneyRepository);
        reset(tokenGenerator);
    }

    @Test
    void test_sprinkleMoney_success_when_existing_empty() {
        when(sprinkledMoneyRepository.findAllByRoomId("MOCK-ROOM-ID")).thenReturn(List.of());
        when(tokenGenerator.generate(any())).thenReturn("ABC");
        when(sprinkledMoneyRepository.save(any())).thenReturn(new SprinkledMoney());

        sprinkleService.sprinkleMoney(1234L, "MOCK-ROOM-ID", 3000, 3);

        verify(tokenGenerator).generate(argThat(Set::isEmpty));
        verify(sprinkledMoneyRepository).save(argThat(it -> it.getToken().equals("ABC")
                && it.getDividedMoney().size() == 3
                && it.getCreatorId().equals(1234L)
                && it.getRoomId().equals("MOCK-ROOM-ID")
                && it.getDividedMoney().stream().mapToInt(DividedMoney::getAmount).sum() == 3000
        ));
    }


    @Test
    void test_sprinkleMoney_success_when_have_existing() {
        when(sprinkledMoneyRepository.findAllByRoomId("MOCK-ROOM-ID")).thenReturn(List.of(new SprinkledMoney("123", "", 0L, 123, 3)));
        when(tokenGenerator.generate(any())).thenReturn("ABC");
        when(sprinkledMoneyRepository.save(any())).thenReturn(new SprinkledMoney());

        sprinkleService.sprinkleMoney(1234L, "MOCK-ROOM-ID", 3000, 3);

        verify(tokenGenerator).generate(argThat(it -> it.contains("123")));
        verify(sprinkledMoneyRepository).save(argThat(it -> it.getToken().equals("ABC")
                && it.getDividedMoney().size() == 3
                && it.getCreatorId().equals(1234L)
                && it.getRoomId().equals("MOCK-ROOM-ID")
                && it.getDividedMoney().stream().mapToInt(DividedMoney::getAmount).sum() == 3000
        ));
    }
}