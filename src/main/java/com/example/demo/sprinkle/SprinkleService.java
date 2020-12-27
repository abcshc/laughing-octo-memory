package com.example.demo.sprinkle;

public interface SprinkleService {
    String sprinkle(Long userId, String roomId, int amount, int divided);

    int receive(Long userId, String roomId, String token);
}
