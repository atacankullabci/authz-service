package com.authservice.domain.service;

public interface PasswordEncoder {
    String encode(String rawPassword);

    boolean matches(String password, String passwordHash);
}
