package com.auth_service.authz_service.domain.service;

public interface PasswordEncoder {
    String encode(String rawPassword);
}
