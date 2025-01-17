package com.mars.expedition.services;


import com.mars.expedition.domain.DTO.GameSessionDTO;
import com.mars.expedition.domain.model.GameSession;
import com.mars.expedition.repository.GameSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class GameSessionService {
    private final GameSessionRepository gameSessionRepository;

    public GameSessionService(GameSessionRepository gameSessionRepository) {this.gameSessionRepository = gameSessionRepository;}

    public GameSessionDTO addGameSession(GameSessionDTO gameSessionDTO) {
        GameSession gameSession  = convertDTOToEntity(gameSessionDTO);
        return convertEntityToDTO(gameSessionRepository.save(gameSession));
    }
    public Optional<GameSessionDTO> findGameSessionById(Long id) {
        Optional<GameSession> optionalGameSession = gameSessionRepository.findById(id);
        if (optionalGameSession.isPresent()) {
            GameSessionDTO gameSessionDTO = convertEntityToDTO(optionalGameSession.get());
            return Optional.of(gameSessionDTO);
        }
        return Optional.empty();
    }
    public void deleteGameSession(Long id ) {
        gameSessionRepository.deleteById(id);
    }
    public List<GameSessionDTO> getAll() {
        Iterable<GameSession> allGameSessions = gameSessionRepository.findAll();
        return StreamSupport.stream(allGameSessions.spliterator(),false)
                .map(this::convertEntityToDTO).toList();
    }

    public GameSession convertDTOToEntity(GameSessionDTO gameSessionDTO) {
        return new GameSession(gameSessionDTO.getGameState());
    }
    public GameSessionDTO convertEntityToDTO(GameSession gameSession) {
        GameSessionDTO gameSessionDTO = new GameSessionDTO();
        gameSessionDTO.setId(gameSession.getId());
        gameSessionDTO.setGameState(gameSession.getGameState());
        return  gameSessionDTO;
    }
}
