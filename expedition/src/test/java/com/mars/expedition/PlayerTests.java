package com.mars.expedition;

import com.mars.expedition.domain.DTO.PlayerDTO;
import com.mars.expedition.domain.model.Player;
import com.mars.expedition.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import com.mars.expedition.services.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RequiredArgsConstructor
@SpringBootTest
class PlayerTests {

    @Autowired
    private  PlayerRepository playerRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  PlayerService playerService;

    private PlayerDTO createTestPlayer(String username, String email, String password) {
        Player player = new Player(username, email, password);
        return playerService.convertEntityToDTO(player);
    }

    @BeforeEach
    void clearDatabase() {
        playerRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("providePlayerDataForAddPlayer")
    void TestAddPlayer(String username, String email, String password) {
        PlayerDTO testPlayerDTO = createTestPlayer(username, email, password);
        PlayerDTO savedPlayer = playerService.addPlayer(testPlayerDTO);
        assertNotNull(savedPlayer);
    }

    @ParameterizedTest
    @MethodSource("providePlayerDataForFindPlayerById")
    void TestFindPlayerById(String username, String email, String password) {
        PlayerDTO testPlayerDTO = createTestPlayer(username, email, password);
        PlayerDTO savedPlayer = playerService.addPlayer(testPlayerDTO);

        PlayerDTO foundPlayer = playerService.findPlayerById(savedPlayer.getId()).orElse(null);
        assertNotNull(foundPlayer);
        assertEquals(savedPlayer.getId(), foundPlayer.getId());
        assertEquals(savedPlayer.getUsername(), foundPlayer.getUsername());
        assertEquals(savedPlayer.getEmail(), foundPlayer.getEmail());
        assertEquals(savedPlayer.getPassword(), foundPlayer.getPassword());
    }

    @ParameterizedTest
    @MethodSource("providePlayerDataForUpdatePlayer")
    void testUpdatePlayer(String username, String email, String password) {
        PlayerDTO playerDTO = createTestPlayer(username, email, password);
        playerDTO = playerService.addPlayer(playerDTO);

        playerDTO.setUsername("UpdatedPlayer");
        playerDTO.setPassword("NewPassword123");
        Optional<PlayerDTO> updatedPlayerDTO = playerService.updatePlayer(playerDTO.getId(), playerDTO);

        assertTrue(updatedPlayerDTO.isPresent());
        PlayerDTO updatedPlayer = updatedPlayerDTO.get();
        assertEquals("UpdatedPlayer", updatedPlayer.getUsername());
        assertNotEquals(password, updatedPlayer.getPassword());
        assertTrue(passwordEncoder.matches("NewPassword123", updatedPlayer.getPassword()));
    }

    @ParameterizedTest
    @MethodSource("providePlayerDataForDeletePlayer")
    void testDeletePlayer(String username, String email, String password) {
        PlayerDTO playerDTO = createTestPlayer(username, email, password);
        playerDTO = playerService.addPlayer(playerDTO);

        Optional<PlayerDTO> deletedPlayerDTO = playerService.deletePlayer(playerDTO.getId());

        assertTrue(deletedPlayerDTO.isPresent());
        PlayerDTO deletedPlayer = deletedPlayerDTO.get();
        assertEquals(playerDTO.getId(), deletedPlayer.getId());

        Optional<Player> playerAfterDelete = playerRepository.findById(playerDTO.getId());
        assertFalse(playerAfterDelete.isPresent());
    }

    @Test
    void testGetAllPlayers() {
        PlayerDTO playerDTO1 = createTestPlayer("Player1", "player1@email.com", "Password1");
        PlayerDTO playerDTO2 = createTestPlayer("Player2", "player2@email.com", "Password2");
        PlayerDTO playerDTO3 = createTestPlayer("Player3", "player3@email.com", "Password3");
        PlayerDTO playerDTO4 = createTestPlayer("Player4", "player4@email.com", "Password4");

        playerDTO1 = playerService.addPlayer(playerDTO1);
        playerDTO2 = playerService.addPlayer(playerDTO2);
        playerDTO3 = playerService.addPlayer(playerDTO3);
        playerDTO4 = playerService.addPlayer(playerDTO4);

        List<PlayerDTO> players = playerService.getAll();

        assertNotNull(players);
        assertEquals(4, players.size());
        PlayerDTO finalPlayerDTO1 = playerDTO1;
        assertTrue(players.stream().anyMatch(player -> player.getId().equals(finalPlayerDTO1.getId())));
        PlayerDTO finalPlayerDTO2 = playerDTO2;
        assertTrue(players.stream().anyMatch(player -> player.getId().equals(finalPlayerDTO2.getId())));
        PlayerDTO finalPlayerDTO3 = playerDTO3;
        assertTrue(players.stream().anyMatch(player -> player.getId().equals(finalPlayerDTO3.getId())));
        PlayerDTO finalPlayerDTO4 = playerDTO4;
        assertTrue(players.stream().anyMatch(player -> player.getId().equals(finalPlayerDTO4.getId())));

    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> providePlayerDataForAddPlayer() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer", "test@email.com", "Xk8#Df9$Tz1@Lp"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer2", "test2@email.com", "Password123"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer3", "test3@email.com", "Password1234"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer4", "test4@email.com", "Password12345")
        );
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> providePlayerDataForFindPlayerById() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer", "test@email.com", "Xk8#Df9$Tz1@Lp"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer2", "test2@email.com", "Password123"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer3", "test3@email.com", "Password1234"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer4", "test4@email.com", "Password12345")
                );
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> providePlayerDataForUpdatePlayer() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer", "test@email.com", "Xk8#Df9$Tz1@Lp"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer2", "test2@email.com", "Password123"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer3", "test3@email.com", "Password1234"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer4", "test4@email.com", "Password12345")
        );
    }

    private static Stream<Arguments> providePlayerDataForDeletePlayer() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer", "test@email.com", "Xk8#Df9$Tz1@Lp"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer2", "test2@email.com", "Password123"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer3", "test3@email.com", "Password1234"),
                org.junit.jupiter.params.provider.Arguments.of("TestPlayer4", "test4@email.com", "Password12345")
        );
    }

}
