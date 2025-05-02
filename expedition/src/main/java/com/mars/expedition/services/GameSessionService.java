package com.mars.expedition.services;


import com.mars.expedition.domain.DTO.GameSessionDTO;
import com.mars.expedition.domain.model.GameSession;
import com.mars.expedition.repository.GameSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameSessionService {
    private final GameSessionRepository gameSessionRepository;

    public Optional<GameSessionDTO> getGameSession(String userId) {
        Optional<GameSession> session = gameSessionRepository.findByUserId(userId);
        return session.map(this::convertEntityToDTO);
    }

    public GameSessionDTO addGameSession(String userId) {
        GameSession gameSession  = new GameSession(userId, "{}");
        return convertEntityToDTO(gameSessionRepository.save(gameSession));
    }

    @Transactional
    public Optional<GameSessionDTO> updateGameSession(String userId, String gameState){
        Optional<GameSession> existingGameSession = gameSessionRepository.findByUserId(userId);

        if (existingGameSession.isPresent()) {
            GameSession gameSession = existingGameSession.get();
            gameSession.setGameState(gameState);

            gameSessionRepository.save(gameSession);

            return Optional.of(convertEntityToDTO(gameSession));
        } else {
            return Optional.empty();
        }
    }

    public void deleteGameSession(String userId) {
        Optional<GameSession> optionalGameSession = gameSessionRepository.findByUserId(userId);
        optionalGameSession.ifPresent(gameSession -> gameSessionRepository.deleteById(gameSession.getId()));
    }

    public GameSessionDTO convertEntityToDTO(GameSession gameSession) {
        return new GameSessionDTO(gameSession.getId(), gameSession.getUserId(), gameSession.getGameState());
    }
}
