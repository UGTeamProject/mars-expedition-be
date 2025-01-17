package com.mars.expedition.repository;

import com.mars.expedition.domain.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Long> {
}
