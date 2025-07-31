package com.authservice.infrastructure.security;

import com.authservice.domain.service.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl implements PasswordEncoder {

    private final BCryptPasswordEncoder encoder;

    public PasswordEncoderImpl(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String password, String passwordHash) {
        return encoder.matches(password, passwordHash);
    }
}
