package com.example.demo.sprinkle;

import com.example.demo.sprinkle.exception.SprinkledMoneyTokenCountOutOfBoundsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TokenGeneratorImplTest {
    private final Random random = mock(Random.class);
    private TokenGeneratorImpl tokenGenerator = new TokenGeneratorImpl();

    @BeforeEach
    void setUp() {
        tokenGenerator = new TokenGeneratorImpl("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", random);
    }

    @AfterEach
    void tearDown() {
        reset(random);
    }

    @Test
    void test_generate_success_when_random_nextInt_return_MIN_0() {
        when(random.nextInt(anyInt())).thenReturn(0);

        assertEquals("aaa", tokenGenerator.generate(Set.of()));
    }

    @Test
    void test_generate_success_when_random_nextInt_return_MAX_238327() {
        when(random.nextInt(anyInt())).thenReturn(238327);

        assertEquals("000", tokenGenerator.generate(Set.of()));
    }

    @Test
    void test_generate_success_return_bbb() {
        when(random.nextInt(anyInt())).thenReturn(3907);

        assertEquals("bbb", tokenGenerator.generate(Set.of()));
    }

    @Test
    void test_generate_success_when_has_existing() {
        when(random.nextInt(anyInt())).thenReturn(3907);

        assertEquals("bbc", tokenGenerator.generate(Set.of("bbb")));
    }

    @Test
    void test_generate_success_when_has_existing2() {
        when(random.nextInt(anyInt())).thenReturn(3907);

        assertEquals("bbd", tokenGenerator.generate(Set.of("bbb", "bbc")));
    }

    @Test
    void test_generate_throw_Exception_when_existing_set_is_full() {
        tokenGenerator = new TokenGeneratorImpl("ab", new Random());
        when(random.nextInt(anyInt())).thenReturn(0);

        assertThrows(SprinkledMoneyTokenCountOutOfBoundsException.class, () -> tokenGenerator.generate(Set.of("aaa", "aab", "aba", "abb", "baa", "bab", "bba", "bbb")));
    }
}