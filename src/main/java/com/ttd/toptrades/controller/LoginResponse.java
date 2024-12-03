package com.ttd.toptrades.controller;

import java.util.Set;

public class LoginResponse {
    private String username;
    private String token;
    private Set<String> roles;

    public LoginResponse(String username, Set<String> roles,String token) {
        this.username = username;
        this.roles = roles;
        this.token =token;
    }

    public String getUsername() {
        return username;
    }
    public String getToken() {
        return token;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
