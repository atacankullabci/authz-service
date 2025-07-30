package com.auth_service.authz_service.application.usecase;

// Input port

public interface LoginUserUseCase {
    LoginResponse login(LoginRequest request);

    record LoginRequest(String email, String password) {}
    record LoginResponse(String accessToken, String refreshToken) {}
}
