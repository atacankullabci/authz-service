package com.auth_service.authz_service.domain.service;

import com.auth_service.authz_service.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    void save(User user);
}
