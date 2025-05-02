package com.mars.expedition.repository;


import com.mars.expedition.domain.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameSessionRepository extends JpaRepository<GameSession,Long> {
    Optional<GameSession> findByUserId(String userId);
}
