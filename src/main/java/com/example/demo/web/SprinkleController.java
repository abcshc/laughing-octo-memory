package com.example.demo.web;

import com.example.demo.sprinkle.SprinkleService;
import com.example.demo.web.request.SprinkleRequest;
import com.example.demo.web.response.SprinkleResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SprinkleController {
    private final SprinkleService sprinkleService;

    public SprinkleController(SprinkleService sprinkleService) {
        this.sprinkleService = sprinkleService;
    }

    @PostMapping("/sprinkle")
    @ResponseBody
    public SprinkleResponse postSprinkle(
            @RequestHeader(value = "X-USER-ID") Long userId,
            @RequestHeader(value = "X-ROOM-ID") String roomId,
            @RequestBody SprinkleRequest request) {
        return new SprinkleResponse(sprinkleService.sprinkleMoney(userId, roomId, request.getAmount(), request.getDivided()));
    }
}
