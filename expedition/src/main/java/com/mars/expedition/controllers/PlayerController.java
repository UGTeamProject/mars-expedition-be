package com.mars.expedition.controllers;

import com.mars.expedition.domain.DTO.PlayerDTO;
import com.mars.expedition.domain.model.Player;
import com.mars.expedition.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {this.playerService = playerService;}

    @PostMapping("/api/player")
    ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO) {
        return new ResponseEntity<>(playerService.addPlayer(playerDTO), HttpStatus.CREATED);
    }
    @GetMapping("/api/player/{id}")
    ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        Optional<PlayerDTO> player = playerService.findPlayerById((id));
        return player.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/api/player")
    ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        return new ResponseEntity<>(playerService.getAll(),HttpStatus.OK);
    }

}
