package com.example.demo.sprinkle;

import com.example.demo.sprinkle.exception.SprinkledMoneyTokenCountOutOfBoundsException;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;

@Component
public class TokenGeneratorImpl implements TokenGenerator {
    private String tokens;
    private int length;
    private int max;
    private Random random;

    public TokenGeneratorImpl(String token, Random random) {
        this.tokens = token;
        this.length = tokens.length();
        this.max = (int) Math.pow(length, 3);
        this.random = random;
    }

    public TokenGeneratorImpl() {
        this.tokens = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        this.length = tokens.length();
        this.max = (int) Math.pow(length, 3);
        this.random = new Random();
    }

    @Override
    public String generate(Set<String> existing) {
        if (existing.size() >= max) {
            throw new SprinkledMoneyTokenCountOutOfBoundsException();
        }
        int number = random.nextInt(max);
        String token = convert(number);
        while (existing.contains(token)) {
            number = (number + 1) % max;
            token = convert(number);
        }

        return token;
    }

    private String convert(int number) {
        int first = number % length;
        int second = number <= length ? 0 : (number / length) % length;
        int third = second == 0 || (number / length) <= length ? 0 : (number / length) / length;

        return "" + tokens.charAt(third) + tokens.charAt(second) + tokens.charAt(first);
    }
}
