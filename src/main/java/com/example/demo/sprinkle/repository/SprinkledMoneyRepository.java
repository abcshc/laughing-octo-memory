package com.example.demo.sprinkle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SprinkledMoneyRepository extends JpaRepository<SprinkledMoney, String> {
    List<SprinkledMoney> findAllByRoomId(String roomId);

    Optional<SprinkledMoney> findByRoomIdAndToken(String roomId, String token);

    Optional<SprinkledMoney> findByCreatorIdAndToken(Long creatorId, String token);
}
