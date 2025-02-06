package com.mars.expedition.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;
}
