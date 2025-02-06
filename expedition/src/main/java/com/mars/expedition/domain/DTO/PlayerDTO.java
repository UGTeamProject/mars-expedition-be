package com.mars.expedition.domain.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.service.annotation.PatchExchange;

@Data
public class PlayerDTO {
    private Long id;
    @NotNull(message = "Name can not be null.")
    private String username;
    @Email
    private String email;
    @NotNull(message = "Password can not be null.")
    @JsonIgnore
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+.<>-]).{8,16}$")
    private String password;
}
