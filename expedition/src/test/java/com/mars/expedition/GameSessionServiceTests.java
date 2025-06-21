package com.mars.expedition;

import com.mars.expedition.domain.DTO.GameSessionDTO;
import com.mars.expedition.domain.model.GameSession;
import com.mars.expedition.repository.GameSessionRepository;
import com.mars.expedition.services.GameSessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameSessionServiceTests {

    @Mock
    private GameSessionRepository gameSessionRepository;

    @InjectMocks
    private GameSessionService gameSessionService;

    @ParameterizedTest
    @MethodSource("provideGameSessionDataForAddGameSession")
    void testAddGameSession(String userId) {
        GameSession expectedGameSession = new GameSession(userId);

        when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(expectedGameSession);

        GameSessionDTO savedGameSession = gameSessionService.addGameSession(userId);
        Assertions.assertEquals(savedGameSession.userId(), userId);
    }

    @ParameterizedTest
    @MethodSource("provideGameSessionDataForGetGameSession")
    void testGetGameSession(String userId) {

        GameSession expectedGameSession = new GameSession(userId);

        when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.of(expectedGameSession));

        GameSessionDTO foundGameSession = gameSessionService.getGameSession(userId).get();

        Assertions.assertEquals(foundGameSession.userId(), userId);
    }

    @ParameterizedTest
    @MethodSource("provideGameSessionDataForGetGameSessionWithError")
    void testGetGameSessionWithError(String userId) {

        when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.empty());

        Optional<GameSessionDTO> foundGameSession = gameSessionService.getGameSession(userId);

        Assertions.assertTrue(foundGameSession.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideGameSessionDataForUpdateGameSession")
    void testUpdateGameSession(String userId, String oldState, String newState) {

        GameSession oldGameSession = new GameSession(userId, oldState);
        GameSession expectedGameSession = new GameSession(userId, newState);

        when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.of(oldGameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(expectedGameSession);

        GameSessionDTO updatedGameSession = gameSessionService.updateGameSession(userId, newState).get();

        Assertions.assertEquals(updatedGameSession.userId(), userId);
        Assertions.assertEquals(updatedGameSession.gameState(), newState);
    }

    @ParameterizedTest
    @MethodSource("provideGameSessionDataForUpdateGameSessionWithError")
    void testUpdateGameSessionWithError(String userId, String newState) {

        when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.empty());

        Optional<GameSessionDTO> updatedGameSession = gameSessionService.updateGameSession(userId, newState);

        Assertions.assertTrue(updatedGameSession.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideGameSessionDataForDeleteGameSession")
    void testDeleteGameSession(String userId) {

        GameSession existingGameSession = new GameSession(userId);

        when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.of(existingGameSession));

        Assertions.assertDoesNotThrow(() -> {
            gameSessionService.deleteGameSession(userId);
        });
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> provideGameSessionDataForGetGameSession() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("1"),
                org.junit.jupiter.params.provider.Arguments.of("2"),
                org.junit.jupiter.params.provider.Arguments.of("3"),
                org.junit.jupiter.params.provider.Arguments.of("4")
        );
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> provideGameSessionDataForGetGameSessionWithError() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("1"),
                org.junit.jupiter.params.provider.Arguments.of("2"),
                org.junit.jupiter.params.provider.Arguments.of("3"),
                org.junit.jupiter.params.provider.Arguments.of("4")
        );
    }

    private static Stream<Arguments> provideGameSessionDataForAddGameSession() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("1"),
                org.junit.jupiter.params.provider.Arguments.of("2"),
                org.junit.jupiter.params.provider.Arguments.of("3"),
                org.junit.jupiter.params.provider.Arguments.of("4")
        );
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> provideGameSessionDataForUpdateGameSession() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("1", "{test:1}", "{test:1new}"),
                org.junit.jupiter.params.provider.Arguments.of("2", "{test:2}", "{test:2new}"),
                org.junit.jupiter.params.provider.Arguments.of("3", "{test:3}", "{test:3new}"),
                org.junit.jupiter.params.provider.Arguments.of("4", "{test:4}", "{test:4new}")
        );
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> provideGameSessionDataForUpdateGameSessionWithError() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("1", "{test:1}", "{test:1new}"),
                org.junit.jupiter.params.provider.Arguments.of("2", "{test:2}", "{test:2new}"),
                org.junit.jupiter.params.provider.Arguments.of("3", "{test:3}", "{test:3new}"),
                org.junit.jupiter.params.provider.Arguments.of("4", "{test:4}", "{test:4new}")
        );
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> provideGameSessionDataForDeleteGameSession() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("1"),
                org.junit.jupiter.params.provider.Arguments.of("2"),
                org.junit.jupiter.params.provider.Arguments.of("3"),
                org.junit.jupiter.params.provider.Arguments.of("4")
        );
    }
}