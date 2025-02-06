package com.mars.expedition.domain.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;

    public Player(String username) {
        this.username = username;
    }
}
