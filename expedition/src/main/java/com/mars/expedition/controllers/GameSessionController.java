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
@RequestMapping("/api/gameSession")
public class GameSessionController {
    private final GameSessionService gameSessionService;

    public GameSessionController(GameSessionService gameSessionService) {this.gameSessionService = gameSessionService;}

    @PostMapping
    ResponseEntity<GameSessionDTO> addGameSession(@RequestBody GameSessionDTO gameSessionDTO) {
        return new ResponseEntity<>(gameSessionService.addGameSession(gameSessionDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    ResponseEntity<GameSessionDTO> getGameSessionById(@PathVariable Long id) {
        Optional<GameSessionDTO> gameSession = gameSessionService.findGameSessionById((id));
        return gameSession.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping
    ResponseEntity<List<GameSessionDTO>> getAllGameSessions() {
        return new ResponseEntity<>(gameSessionService.getAll(),HttpStatus.OK);
    }
    @PostMapping("/{id}")
    ResponseEntity<GameSessionDTO>updateGameSession(@PathVariable Long id,@RequestBody GameSessionDTO gameSessionDTO) {
        Optional<GameSessionDTO> gameSession = gameSessionService.updateGameSession(id,gameSessionDTO);
        return gameSession.map(dto -> new ResponseEntity<>(dto,HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String>deleteGameSession(@PathVariable Long id){
        Optional<GameSessionDTO> gameSession = gameSessionService.deleteGameSession(id);
        if(gameSession.isPresent()){
            return new ResponseEntity<>(String.format("Game session with id %s deleted successfully.", id), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(String.format("Game session with id %s not found", id), HttpStatus.NOT_FOUND);
    }
}
