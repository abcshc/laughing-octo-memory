package com.example.demo.web.response;

import com.example.demo.sprinkle.repository.DividedMoney;
import com.example.demo.sprinkle.repository.SprinkledMoney;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime sprinkledTime;
    private int amount = 0;
    private int receivedAmount = 0;
    private List<CheckReceivedResponse> received = new ArrayList<>();

    public CheckResponse(SprinkledMoney sprinkledMoney) {
        this.sprinkledTime = sprinkledMoney.getCreatedTime();
        List<DividedMoney> dividedMoney = sprinkledMoney.getDividedMoney();
        dividedMoney.forEach(it -> {
            if (it.isReceived()) {
                this.receivedAmount += it.getAmount();
                this.received.add(new CheckReceivedResponse(it.getReceiverId(), it.getAmount()));
            }
            this.amount += it.getAmount();
        });
    }

    public LocalDateTime getSprinkledTime() {
        return sprinkledTime;
    }

    public int getAmount() {
        return amount;
    }

    public int getReceivedAmount() {
        return receivedAmount;
    }

    public List<CheckReceivedResponse> getReceived() {
        return received;
    }
}
