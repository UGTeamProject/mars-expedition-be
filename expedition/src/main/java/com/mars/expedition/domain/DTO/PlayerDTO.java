package com.mars.expedition.domain.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlayerDTO {
    private Long id;
    @NotNull(message = "Name can not be null.")
    private String name;
    @NotNull(message = "Password can not be null.")
    @JsonIgnore
    private String password;
}
