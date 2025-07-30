package com.auth_service.authz_service.application.usecase;

// Input port

public interface RegisterUserUseCase {
    void register(RegisterUserCommand command);

    record RegisterUserCommand(String email, String password) {}
}
