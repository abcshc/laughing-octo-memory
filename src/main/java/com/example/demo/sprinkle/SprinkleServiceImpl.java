package com.example.demo.sprinkle;

import com.example.demo.sprinkle.repository.SprinkledMoney;
import com.example.demo.sprinkle.repository.SprinkledMoneyRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SprinkleServiceImpl implements SprinkleService {
    private final SprinkledMoneyRepository sprinkledMoneyRepository;
    private final TokenGenerator tokenGenerator;

    public SprinkleServiceImpl(SprinkledMoneyRepository sprinkledMoneyRepository, TokenGenerator tokenGenerator) {
        this.sprinkledMoneyRepository = sprinkledMoneyRepository;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public String sprinkleMoney(Long userId, String roomId, int amount, int divided) {
        Set<String> existing = sprinkledMoneyRepository.findAllByRoomId(roomId)
                .stream()
                .map(SprinkledMoney::getToken)
                .collect(Collectors.toSet());
        String token = tokenGenerator.generate(existing);

        return sprinkledMoneyRepository.save(new SprinkledMoney(token, roomId, userId, amount, divided)).getToken();
    }
}
