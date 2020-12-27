package com.example.demo.sprinkle.repository;

import com.example.demo.sprinkle.exception.SprinkledMoneyCreatorReceiverSameException;
import com.example.demo.sprinkle.exception.SprinkledMoneyDuplicateReceiveException;
import com.example.demo.sprinkle.exception.SprinkledMoneyExpiredException;
import com.example.demo.sprinkle.exception.SprinkledMoneyMinAmountPerCountException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class SprinkledMoney {
    @Id
    private String token;
    private String roomId;
    private Long creatorId;
    @OneToMany(mappedBy = "sprinkledMoney", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DividedMoney> dividedMoney;
    private LocalDateTime createdTime;

    public SprinkledMoney() {
    }

    public SprinkledMoney(String token, String roomId, Long creatorId, int amount, int count, LocalDateTime createdTime) {
        this.token = token;
        this.roomId = roomId;
        this.creatorId = creatorId;
        this.dividedMoney = divideAmount(amount, count);
        this.createdTime = createdTime;
    }

    private List<DividedMoney> divideAmount(int amount, int count) {
        if (amount < count) {
            throw new SprinkledMoneyMinAmountPerCountException();
        }

        Random random = new Random();
        int remainingMoney = amount;
        ArrayList<DividedMoney> divided = new ArrayList<>();
        for (int i = 0; i < count - 1; i++) {
            int remainingCount = count - i;
            int current = remainingMoney == remainingCount ? 1 : random.nextInt(remainingMoney - remainingCount) + 1;
            remainingMoney = remainingMoney - current;
            divided.add(new DividedMoney(this, current));
        }
        divided.add(new DividedMoney(this, remainingMoney));

        return divided;
    }

    public int receive(Long userId) {
        if (creatorId.equals(userId)) {
            throw new SprinkledMoneyCreatorReceiverSameException();
        }

        if (createdTime.plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new SprinkledMoneyExpiredException();
        }

        return dividedMoney.stream()
                .filter(it -> {
                    if (userId.equals(it.getReceiverId())) {
                        throw new SprinkledMoneyDuplicateReceiveException();
                    }
                    return !it.isReceived();
                })
                .findFirst()
                .orElseThrow(SprinkledMoneyExpiredException::new)
                .receive(userId);
    }

    public String getToken() {
        return token;
    }

    public String getRoomId() {
        return roomId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public List<DividedMoney> getDividedMoney() {
        return dividedMoney;
    }
}
