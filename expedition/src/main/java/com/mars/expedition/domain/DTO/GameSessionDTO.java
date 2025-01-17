package com.mars.expedition.domain.DTO;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameSessionDTO {
    private Long id;
    @NotNull(message = "Game state can not be null.")
    private String gameState;
}
