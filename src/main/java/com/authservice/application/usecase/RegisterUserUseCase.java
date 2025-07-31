package com.authservice.application.usecase;

// Input port

public interface RegisterUserUseCase {
    void register(RegisterUserCommand command);

    record RegisterUserCommand(String email, String password) {}
}
