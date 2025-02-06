package com.mars.expedition.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Get the Authorization header
        final String jwt; // JWT string
        final String username; // Extracted username

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // No JWT, continue with the filter chain
            return;
        }

        jwt = authHeader.substring(7); // Extract JWT token from header (removes "Bearer " prefix)
        username = jwtUtils.extractUsername(jwt); // Extract username from JWT

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // If token is valid and there's no current authentication
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // Load user details

            if (jwtUtils.validateToken(jwt, userDetails)) {
                // If token is valid, create authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set authentication details (e.g., IP address, session details, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response); // Continue with the filter chain
    }
}
