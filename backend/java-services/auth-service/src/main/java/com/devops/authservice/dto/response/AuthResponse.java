package com.devops.authservice.dto.response;

import com.devops.authservice.model.Role;

import java.util.Set;

public class AuthResponse {

    private String id;
    private String email;
    private String fullName;
    private Set<Role> roles;
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public AuthResponse(String id, String email, String fullName, Set<Role> roles, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
