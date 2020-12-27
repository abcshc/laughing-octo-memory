package com.example.demo.sprinkle;

import com.example.demo.sprinkle.repository.SprinkledMoney;

public interface SprinkleService {
    String sprinkle(Long userId, String roomId, int amount, int divided);

    int receive(Long userId, String roomId, String token);

    SprinkledMoney check(Long userId, String token);
}
