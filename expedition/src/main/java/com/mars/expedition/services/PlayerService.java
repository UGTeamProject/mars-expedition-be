package com.mars.expedition.services;

import com.mars.expedition.exceptions.*;
import com.mars.expedition.domain.DTO.PlayerDTO;
import com.mars.expedition.domain.model.Player;
import com.mars.expedition.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class PlayerService {

    final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    public PlayerDTO addPlayer(PlayerDTO playerDTO) {
        Player player = convertDTOToEntity(playerDTO);
        return convertEntityToDTO(playerRepository.save(player));
    }
    public Optional<PlayerDTO> findPlayerById(Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            PlayerDTO playerDTO = convertEntityToDTO(optionalPlayer.get());
            return Optional.of(playerDTO);
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<PlayerDTO> updatePlayer(Long id, PlayerDTO playerDTO){
        Optional<Player> existingPlayer = playerRepository.findById(id);
        if (existingPlayer.isPresent()) {
            Player player = existingPlayer.get();
            player.setName(playerDTO.getName());
            player.setPassword(player.getPassword());
            return Optional.of(convertEntityToDTO(player));
        }
        return Optional.empty();
    }

    public Optional<PlayerDTO> deletePlayer(Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            playerRepository.deleteById(id);
            PlayerDTO playerDTO = convertEntityToDTO(optionalPlayer.get());
            return Optional.of(playerDTO);
        }
        return Optional.empty();
    }
    public List<PlayerDTO> getAll() {
        Iterable<Player> allPlayers = playerRepository.findAll();
        return StreamSupport.stream(allPlayers.spliterator(),false)
                .map(this::convertEntityToDTO).toList();
    }


    public Player convertDTOToEntity(PlayerDTO playerDTO) {
        return new Player(playerDTO.getName());
    }
    public PlayerDTO convertEntityToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        return  playerDTO;
    }
}
