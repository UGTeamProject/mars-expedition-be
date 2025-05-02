package com.mars.expedition.domain.DTO;

import jakarta.validation.constraints.NotNull;

public record GameSessionDTO(
        Long id,
        String userId,
        @NotNull(message = "Game state cannot be null.")
        String gameState
) {}
