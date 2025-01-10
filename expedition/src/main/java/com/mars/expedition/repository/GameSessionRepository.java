package com.mars.expedition.repository;


import com.mars.expedition.domain.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSessionRepository extends JpaRepository<GameSession,Long> {
}
