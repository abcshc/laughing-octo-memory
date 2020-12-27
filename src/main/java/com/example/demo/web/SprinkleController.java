package com.example.demo.web;

import com.example.demo.sprinkle.SprinkleService;
import com.example.demo.web.request.ReceiveRequest;
import com.example.demo.web.request.SprinkleRequest;
import com.example.demo.web.response.CheckResponse;
import com.example.demo.web.response.ReceiveResponse;
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
        return new SprinkleResponse(sprinkleService.sprinkle(userId, roomId, request.getAmount(), request.getDivided()));
    }

    @PostMapping("/receive")
    @ResponseBody
    public ReceiveResponse postReceive(
            @RequestHeader(value = "X-USER-ID") Long userId,
            @RequestHeader(value = "X-ROOM-ID") String roomId,
            @RequestBody ReceiveRequest request) {
        return new ReceiveResponse(sprinkleService.receive(userId, roomId, request.getToken()));
    }

    @GetMapping("/check/{token}")
    @ResponseBody
    public CheckResponse getCheck(
            @RequestHeader(value = "X-USER-ID") Long userId,
            @PathVariable String token
    ) {
        return new CheckResponse(sprinkleService.check(userId, token));
    }
}
