package com.mars.expedition.controllers;

import com.mars.expedition.domain.DTO.GameSessionDTO;
import com.mars.expedition.domain.DTO.PlayerDTO;
import com.mars.expedition.repository.GameSessionRepository;
import com.mars.expedition.services.GameSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameSessionController {
    private final GameSessionService gameSessionService;

    public GameSessionController(GameSessionService gameSessionService) {this.gameSessionService = gameSessionService;}

    @PostMapping("/api/gameSession")
    ResponseEntity<GameSessionDTO> addGameSession(@RequestBody GameSessionDTO gameSessionDTO) {
        return new ResponseEntity<>(gameSessionService.addGameSession(gameSessionDTO), HttpStatus.CREATED);
    }
    @GetMapping("/api/gameSession/{id}")
    ResponseEntity<GameSessionDTO> getGameSessionById(@PathVariable Long id) {
        Optional<GameSessionDTO> gameSession = gameSessionService.findGameSessionById((id));
        return gameSession.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/api/gameSession")
    ResponseEntity<List<GameSessionDTO>> getAllGameSessions() {
        return new ResponseEntity<>(gameSessionService.getAll(),HttpStatus.OK);
    }
}
