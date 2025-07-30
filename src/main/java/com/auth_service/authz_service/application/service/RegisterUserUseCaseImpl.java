package com.auth_service.authz_service.application.service;

import com.auth_service.authz_service.application.usecase.RegisterUserUseCase;
import com.auth_service.authz_service.domain.model.Permission;
import com.auth_service.authz_service.domain.model.Role;
import com.auth_service.authz_service.domain.model.User;
import com.auth_service.authz_service.domain.service.PasswordEncoder;
import com.auth_service.authz_service.domain.service.UserRepository;

import java.util.Set;

public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void register(RegisterUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        String encodedPassword = passwordEncoder.encode(command.password());

        Role defaultRole = new Role(null, "USER", Set.of(
                new Permission("READ"),
                new Permission("AUTH_REQUEST")
        ));

        User user = new User(
                null,
                command.email(),
                encodedPassword,
                Set.of(defaultRole),
                true
        );

        userRepository.save(user);
    }
}
