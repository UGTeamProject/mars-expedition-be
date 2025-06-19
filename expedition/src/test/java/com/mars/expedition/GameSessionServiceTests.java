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

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class GameSessionServiceTests {

	@Mock
	private GameSessionRepository gameSessionRepository;
	@InjectMocks
	private GameSessionService gameSessionService;

//	@Test
//	void contextLoads() {
//	}



	@ParameterizedTest
	@MethodSource("provideGameSessionDataForAddGameSession")
	void TestAddGameSession(String userId) {
		 GameSession expectedGameSession = new GameSession(userId);

		 when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.empty());
		 when(gameSessionRepository.save(any(GameSession.class))).thenReturn(expectedGameSession);

		 GameSessionDTO savedGameSession = gameSessionService.addGameSession(userId);
		 Assertions.assertEquals(savedGameSession.userId(),userId);
//		PlayerDTO savedPlayer = playerService.addPlayer(testPlayerDTO);
//		assertNotNull(savedPlayer);
	}

	@ParameterizedTest
	@MethodSource("provideGameSessionDataForUpdateGameSession")
	void TestUpdateGameSession(String userId,String oldState,String newState) {

		GameSession oldGameSession = new GameSession(userId,oldState);
		GameSession expectedGameSession = new GameSession(userId,newState);

		when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.of(oldGameSession));
		when(gameSessionRepository.save(any(GameSession.class))).thenReturn(expectedGameSession);

		GameSessionDTO updatedGameSession = gameSessionService.updateGameSession(userId,newState).get();

		Assertions.assertEquals(updatedGameSession.userId(),userId);
		Assertions.assertEquals(updatedGameSession.gameState(),newState);
//		PlayerDTO savedPlayer = playerService.addPlayer(testPlayerDTO);
//		assertNotNull(savedPlayer);
	}

	@ParameterizedTest
	@MethodSource("provideGameSessionDataForDeleteGameSession")
	void TestDeleteGameSession(String userId ) {

		GameSession existingGameSession = new GameSession(userId);

		when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.of(existingGameSession));
//		when(gameSessionRepository.deleteById(anyLong())).then(doNothing());
		Assertions.assertDoesNotThrow(() -> {gameSessionService.deleteGameSession(userId);});
//		GameSession oldGameSession = new GameSession(userId,oldState);
//		GameSession expectedGameSession = new GameSession(userId,newState);
//
//		when(gameSessionRepository.findByUserId(anyString())).thenReturn(Optional.of(oldGameSession));
//		when(gameSessionRepository.save(any(GameSession.class))).thenReturn(expectedGameSession);
//
//		GameSessionDTO updatedGameSession = gameSessionService.updateGameSession(userId,newState).get();

//		Assertions.assertEquals(updatedGameSession.userId(),userId);
//		Assertions.assertEquals(updatedGameSession.gameState(),newState);
//		PlayerDTO savedPlayer = playerService.addPlayer(testPlayerDTO);
//		assertNotNull(savedPlayer);
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

	private static Stream<org.junit.jupiter.params.provider.Arguments> provideGameSessionDataForDeleteGameSession() {
		return Stream.of(
				org.junit.jupiter.params.provider.Arguments.of("1"),
				org.junit.jupiter.params.provider.Arguments.of("2"),
				org.junit.jupiter.params.provider.Arguments.of("3"),
				org.junit.jupiter.params.provider.Arguments.of("4")
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
