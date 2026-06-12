package com.devops.authservice.controller;

import com.devops.authservice.dto.request.LoginRequest;
import com.devops.authservice.dto.request.RegisterRequest;
import com.devops.authservice.dto.response.AuthResponse;
import com.devops.authservice.dto.response.UserProfileResponse;
import com.devops.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of(
                "service", "auth-service",
                "status", "running"
        );
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public UserProfileResponse me(Authentication authentication) {
        return authService.getProfile(authentication.getName());
    }
}
