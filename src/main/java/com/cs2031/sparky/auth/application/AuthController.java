package com.cs2031.sparky.auth.application;

import com.cs2031.sparky.auth.domain.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    record RegisterRequest(String email, String password, String name) {}
    record LoginRequest(String email, String password) {}

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest req) {
        String token = authService.register(req.email(), req.password(), req.name());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.email(), req.password());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
