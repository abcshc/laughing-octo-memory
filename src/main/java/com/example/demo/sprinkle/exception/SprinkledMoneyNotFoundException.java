package com.example.demo.sprinkle.exception;

public class SprinkledMoneyNotFoundException extends RuntimeException {
    public SprinkledMoneyNotFoundException(String message) {
        super(message);
    }
}
