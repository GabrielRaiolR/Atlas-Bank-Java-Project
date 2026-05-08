package com.atlasbank.auth.web;

import com.atlasbank.auth.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        String role = request.email().endsWith("@atlasbank.com") ? "ADMIN" : "CUSTOMER";
        String token =
                jwtService.generate(request.email(), role);
        return new LoginResponse(token, "Bearer", 60);
    }

    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {
    }
    public record LoginResponse(String accessToken, String tokenType, long expiresInMinutes) {}
}
