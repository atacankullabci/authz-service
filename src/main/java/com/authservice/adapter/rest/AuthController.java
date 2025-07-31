package com.authservice.adapter.rest;

import com.authservice.application.usecase.LoginUserUseCase;
import com.authservice.application.usecase.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
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

    public record RegisterRequest(String email, String password) {
    }

    public record LoginRequest(String email, String password) {
    }
}
