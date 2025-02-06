package com.mars.expedition.repository;

import com.mars.expedition.domain.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Optional<Player> findByUsernameOrEmail(String username, String email);
    Optional<Player> findByUsername(String username);
    Optional<Player> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
