package com.mars.expedition.domain.model;


import javax.print.DocFlavor.STRING;

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

    public Player(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
