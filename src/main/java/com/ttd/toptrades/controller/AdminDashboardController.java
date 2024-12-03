package com.ttd.toptrades.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminDashboardController {

    @GetMapping("/dashboard")
//@PreAuthorize("hasAnyAuthority('SCOPE_email', 'SCOPE_openid')")
    public ResponseEntity<?> getDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Extract username
            String username = authentication.getName();

            // Extract authorities (roles)
            var authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Handle JWT-specific details (if applicable)
            if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
                Jwt jwt = jwtAuthToken.getToken();
                // Extract claims from JWT
                Map<String, Object> claims = jwt.getClaims();

                return ResponseEntity.ok(Map.of(
                        "message", "Welcome to the Dashboard!",
                        "username", username,
                        "roles", authorities,
                        "claims", claims
                ));
            }

            // For other types of authentication, return basic details
            return ResponseEntity.ok(Map.of(
                    "message", "Welcome to the Dashboard!",
                    "username", username,
                    "roles", authorities
            ));
        }

        return ResponseEntity.status(401).body(Map.of("message", "You are not logged in."));
    }


}
