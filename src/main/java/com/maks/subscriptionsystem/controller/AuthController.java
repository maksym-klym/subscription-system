package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.CredentialsDto;
import com.maks.subscriptionsystem.dto.LoginResponse;
import com.maks.subscriptionsystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Authenticate user")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody CredentialsDto credentialsDto) {
        return authService.login(credentialsDto);
    }
}
