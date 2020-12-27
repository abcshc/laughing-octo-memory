package com.example.demo.web.response;

public class CheckReceivedResponse {
    private final Long receiverId;
    private final int amount;

    public CheckReceivedResponse(Long receiverId, int amount) {
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public Long getReceiverId() {
        return receiverId;
    }
}
