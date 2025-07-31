package com.authservice.adapter.rest;

import com.authservice.application.usecase.LoginUserUseCase;
import com.authservice.application.usecase.RegisterUserUseCase;
import com.authservice.domain.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final TokenService tokenService;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase, TokenService tokenService) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
        registerUserUseCase.register(new RegisterUserUseCase.RegisterUserCommand(
                request.email(),
                request.password()
        ));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserUseCase.LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        var response = loginUserUseCase.login(new LoginUserUseCase.LoginRequest(
                request.email(),
                request.password()
        ));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        tokenService.revokeRefreshToken(token);
        return ResponseEntity.ok().build();
    }

    public record RegisterRequest(String email, String password) {
    }

    public record LoginRequest(String email, String password) {
    }
}
