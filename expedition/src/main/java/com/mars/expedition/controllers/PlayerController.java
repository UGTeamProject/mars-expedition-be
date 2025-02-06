package com.mars.expedition.controllers;

import com.mars.expedition.domain.DTO.LoginDTO;
import com.mars.expedition.domain.DTO.PlayerDTO;
import com.mars.expedition.domain.model.Player;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.mars.expedition.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerService playerService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

//    public PlayerController(PlayerService playerService) {this.playerService = playerService;}

    @PostMapping
    ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO) {
        return new ResponseEntity<>(playerService.addPlayer(playerDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/{id}")
    ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        Optional<PlayerDTO> player = playerService.findPlayerById((id));
        return player.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        return new ResponseEntity<>(playerService.getAll(),HttpStatus.OK);
    }

    @PostMapping("/{id}")
    ResponseEntity<PlayerDTO>updatePlayer(@PathVariable Long id,@RequestBody PlayerDTO playerDTO) {
        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = currentUser.getUsername(); // Assuming username is the unique identifier
        // Find the player by id, assuming the id field maps to the player in the DB
        Optional<PlayerDTO> playerOptional = playerService.findPlayerById(id);

        if (playerOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Player not found
        }
        PlayerDTO player = playerOptional.get();

        // Check if the username in the request matches the current authenticated user's username
        if (!player.getUsername().equals(currentUsername)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // User is not allowed to update this player
        }

        // Now, update the player
        Optional<PlayerDTO> updatedPlayer = playerService.updatePlayer(id, playerDTO);

        return updatedPlayer.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
