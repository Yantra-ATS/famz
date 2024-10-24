package com.example.famz.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminDashboardController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Ensure only users with ADMIN role can access
    public String getDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Get the logged-in username
            return "Welcome to the Admin Dashboard, " + username + "!";
        } else {
            return "You are not logged in.";
        }
    }
}
