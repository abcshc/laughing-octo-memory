package com.example.demo.sprinkle.repository;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
public class DividedMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private SprinkledMoney sprinkledMoney;
    private int amount;
    private boolean received = false;
    @Nullable
    private Long receiverId = null;

    public DividedMoney() {
    }

    public DividedMoney(SprinkledMoney sprinkledMoney, int amount) {
        this.sprinkledMoney = sprinkledMoney;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isReceived() {
        return received;
    }

    @Nullable
    public Long getReceiverId() {
        return receiverId;
    }

    public int receive(Long receiverId) {
        this.received = true;
        this.receiverId = receiverId;
        return amount;
    }
}
