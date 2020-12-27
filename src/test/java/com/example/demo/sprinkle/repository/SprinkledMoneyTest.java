package com.example.demo.sprinkle.repository;

import com.example.demo.sprinkle.exception.SprinkledMoneyCreatorReceiverSameException;
import com.example.demo.sprinkle.exception.SprinkledMoneyDuplicateReceiveException;
import com.example.demo.sprinkle.exception.SprinkledMoneyExpiredException;
import com.example.demo.sprinkle.exception.SprinkledMoneyMinAmountPerCountException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SprinkledMoneyTest {
    @Test
    void test_divideAmount() {
        SprinkledMoney sprinkledMoney = new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 3000, 5, LocalDateTime.now());

        assertEquals(5, sprinkledMoney.getDividedMoney().size());
        assertEquals(3000, sprinkledMoney.getDividedMoney().stream().mapToInt(DividedMoney::getAmount).sum());
    }

    @Test
    void test_divideAmount_when_same_price_and_quantity_return_divided_amount_1() {
        SprinkledMoney sprinkledMoney = new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 5, 5, LocalDateTime.now());

        assertEquals(5, sprinkledMoney.getDividedMoney().size());
        assertEquals(5, sprinkledMoney.getDividedMoney().stream().mapToInt(DividedMoney::getAmount).sum());
        assertEquals(5, sprinkledMoney.getDividedMoney().stream().filter(it -> it.getAmount() >= 1).count());
    }

    @Test
    void test_divideAmount_throw_SprinkledMoneyMinAmountPerCountException() {
        assertThrows(SprinkledMoneyMinAmountPerCountException.class, () -> new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 4, 5, LocalDateTime.now()));
    }

    @Test
    void test_receive_success() {
        SprinkledMoney sprinkledMoney = new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 3000, 5, LocalDateTime.now());

        int received1 = sprinkledMoney.receive(234L);
        int received2 = sprinkledMoney.receive(345L);
        int received3 = sprinkledMoney.receive(456L);

        assertTrue(received1 >= 1 && received1 <= 3000);
        assertEquals(234L, sprinkledMoney.getDividedMoney().get(0).getReceiverId());
        assertEquals(received1, sprinkledMoney.getDividedMoney().get(0).getAmount());
        assertTrue(received2 >= 1 && received2 <= 3000);
        assertEquals(345L, sprinkledMoney.getDividedMoney().get(1).getReceiverId());
        assertEquals(received2, sprinkledMoney.getDividedMoney().get(1).getAmount());
        assertTrue(received3 >= 1 && received3 <= 3000);
        assertEquals(456L, sprinkledMoney.getDividedMoney().get(2).getReceiverId());
        assertEquals(received3, sprinkledMoney.getDividedMoney().get(2).getAmount());
    }

    @Test
    void test_receive_throw_SprinkledMoneyCreatorReceiverSameException() {
        SprinkledMoney sprinkledMoney = new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 3000, 5, LocalDateTime.now());

        assertThrows(SprinkledMoneyCreatorReceiverSameException.class, () -> sprinkledMoney.receive(123L));
    }

    @Test
    void test_receive_throw_SprinkledMoneyExpiredException_when_after_10minutes() {
        SprinkledMoney sprinkledMoney = new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 3000, 5, LocalDateTime.now().minusMinutes(10));

        assertThrows(SprinkledMoneyExpiredException.class, () -> sprinkledMoney.receive(234L));
    }

    @Test
    void test_receive_throw_SprinkledMoneyDuplicateReceiveException() {
        SprinkledMoney sprinkledMoney = new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 3000, 5, LocalDateTime.now());
        sprinkledMoney.receive(234L);

        assertThrows(SprinkledMoneyDuplicateReceiveException.class, () -> sprinkledMoney.receive(234L));
    }

    @Test
    void test_receive_throw_SprinkledMoneyExpiredException_when_all_dividedMoney_consumed() {
        SprinkledMoney sprinkledMoney = new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 3000, 2, LocalDateTime.now());
        sprinkledMoney.receive(234L);
        sprinkledMoney.receive(345L);

        assertThrows(SprinkledMoneyExpiredException.class, () -> sprinkledMoney.receive(456L));
    }
}
