package com.mars.expedition.controllers;

import com.mars.expedition.domain.DTO.GameSessionDTO;
import com.mars.expedition.domain.DTO.PlayerDTO;
import com.mars.expedition.domain.model.Player;
import com.mars.expedition.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {this.playerService = playerService;}

    @PostMapping
    ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO) {
        return new ResponseEntity<>(playerService.addPlayer(playerDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        Optional<PlayerDTO> player = playerService.findPlayerById((id));
        return player.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping
    ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        return new ResponseEntity<>(playerService.getAll(),HttpStatus.OK);
    }

    @PostMapping("/{id}")
    ResponseEntity<PlayerDTO>updatePlayer(@PathVariable Long id,@RequestBody PlayerDTO playerDTO) {
        Optional<PlayerDTO> player = playerService.updatePlayer(id,playerDTO);
        return player.map(dto -> new ResponseEntity<>(dto,HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String>deletePlayer(@PathVariable Long id){
        Optional<PlayerDTO> player = playerService.deletePlayer(id);
        if(player.isPresent()){
            return new ResponseEntity<>(String.format("Player with id %s deleted successfully.", id), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(String.format("Player with id %s not found", id), HttpStatus.NOT_FOUND);
    }

}
