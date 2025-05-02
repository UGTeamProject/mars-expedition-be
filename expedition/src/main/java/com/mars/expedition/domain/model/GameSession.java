package com.mars.expedition.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "game_sessions")
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String gameState;

    public GameSession(String gameState) {
        this.gameState = gameState;
    }

    public GameSession(String userId, String gameState) {
        this.userId = userId;
        this.gameState = gameState;
    }
}
