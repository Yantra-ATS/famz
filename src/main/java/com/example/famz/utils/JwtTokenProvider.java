package com.example.famz.utils;

import com.example.famz.services.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

    @Component
    public class JwtTokenProvider {

        private final UserDetailsServiceImpl userDetailsService;
        private final String SECRET_KEY = "your_secret_key"; // Replace with your actual secret key
        private final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

        public JwtTokenProvider(UserDetailsServiceImpl userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        // Generate a JWT token based on user details
        public String createToken(UserDetails userDetails) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", userDetails.getAuthorities());

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
        }

        // Validate the JWT token
        public boolean validateToken(String token) {
            try {
                Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                return false; // Token is invalid or expired
            }
        }

        // Get user authentication from the JWT token
        public Authentication getAuthentication(String token) {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            // Here you can create an authentication object if needed
            // Assuming you have a method to load user details from username
            UserDetails userDetails =userDetailsService.loadUserByUsername(username); // load user by username (e.g., userDetailsService.loadUserByUsername(username));
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }

        // Extract roles from the token (optional, if needed)
        public String extractRoles(String token) {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return claims.get("roles", String.class);
        }
    }
