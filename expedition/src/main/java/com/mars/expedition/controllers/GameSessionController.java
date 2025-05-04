package com.mars.expedition.controllers;

import com.mars.expedition.domain.DTO.GameSessionDTO;
import com.mars.expedition.services.GameSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game-session")
public class GameSessionController {
    private final GameSessionService gameSessionService;

    @PostMapping
    ResponseEntity<GameSessionDTO> createGameSession(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(gameSessionService.addGameSession(userId));
    }

    @GetMapping("/state")
    ResponseEntity<GameSessionDTO> getGameSession(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        Optional<GameSessionDTO> gameSession = gameSessionService.getGameSession((userId));
        return gameSession.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    ResponseEntity<GameSessionDTO>updateGameSession(@AuthenticationPrincipal Jwt jwt, @RequestBody String gameState) {
        String userId = jwt.getSubject();
        Optional<GameSessionDTO> gameSession = gameSessionService.updateGameSession(userId, gameState);
        return gameSession.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete")
    ResponseEntity<String> deleteGameSession(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        gameSessionService.deleteGameSession(userId);
        return ResponseEntity.noContent().build();
    }
}
