package com.authservice.application.service;

import com.authservice.application.usecase.RegisterUserUseCase;
import com.authservice.domain.model.Permission;
import com.authservice.domain.model.Role;
import com.authservice.domain.model.User;
import com.authservice.domain.repository.UserRepository;
import com.authservice.domain.service.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
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
