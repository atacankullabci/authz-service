package com.authservice.application.service;

import com.authservice.application.usecase.LoginUserUseCase;
import com.authservice.domain.event.UserAuthenticatedEvent;
import com.authservice.domain.model.User;
import com.authservice.domain.repository.UserRepository;
import com.authservice.domain.service.AuthEventPublisher;
import com.authservice.domain.service.PasswordEncoder;
import com.authservice.domain.service.TokenService;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthEventPublisher authEventPublisher;

    public LoginUserUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, AuthEventPublisher authEventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authEventPublisher = authEventPublisher;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!user.isActive()) {
            throw new IllegalStateException("User is deactivated");
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        authEventPublisher.publish(new UserAuthenticatedEvent(user.getEmail(), Instant.now()));

        return new LoginResponse(accessToken, refreshToken);
    }
}
