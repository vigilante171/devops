package com.devops.authservice.dto.response;

import com.devops.authservice.model.Role;

import java.util.Set;

public class UserProfileResponse {

    private String id;
    private String email;
    private String fullName;
    private Set<Role> roles;

    public UserProfileResponse(String id, String email, String fullName, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
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
}
