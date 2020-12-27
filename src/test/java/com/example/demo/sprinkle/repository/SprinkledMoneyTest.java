package com.example.demo.sprinkle.repository;

import com.example.demo.sprinkle.exception.SprinkledMoneyMinAmountPerCountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SprinkledMoneyTest {
    @Test
    void test_divideAmount() {
        SprinkledMoney sprinkledMoney = new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 3000, 5);

        assertEquals(5, sprinkledMoney.getDividedMoney().size());
        assertEquals(3000, sprinkledMoney.getDividedMoney().stream().mapToInt(DividedMoney::getAmount).sum());
    }

    @Test
    void test_divideAmount_when_same_price_and_quantity_return_divided_amount_1() {
        SprinkledMoney sprinkledMoney = new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 5, 5);

        assertEquals(5, sprinkledMoney.getDividedMoney().size());
        assertEquals(5, sprinkledMoney.getDividedMoney().stream().mapToInt(DividedMoney::getAmount).sum());
        assertEquals(5, sprinkledMoney.getDividedMoney().stream().filter(it -> it.getAmount() >= 1).count());
    }

    @Test
    void test_divideAmount_throw_SprinkledMoneyMinAmountPerCountException() {
        assertThrows(SprinkledMoneyMinAmountPerCountException.class, () -> new SprinkledMoney("ABC", "MOCK-ROOM-ID", 123L, 4, 5));
    }
}
