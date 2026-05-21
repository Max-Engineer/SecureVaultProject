package com.securevault.api.controller;


import com.securevault.api.dto.LoginRequest;
import com.securevault.api.security.JwtTokenProvider;
import com.securevault.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        if (userService.authenticate(request.email(), request.password())) {
            return tokenProvider.generateToken(request.email());
        }
        throw new RuntimeException("Invalid credentials");
    }
}
