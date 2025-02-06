package com.mars.expedition.services;

import com.mars.expedition.domain.model.Player;
import com.mars.expedition.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomPlayerDetailsService implements UserDetailsService {
    private final PlayerRepository playerRepository;

//    public CustomPlayerDetailsService(PlayerRepository playerRepository) {
//        this.playerRepository = playerRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Player player = playerRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Player not found with username or email: " + usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(
                player.getUsername(), player.getPassword(), Collections.emptyList());
    }
}
