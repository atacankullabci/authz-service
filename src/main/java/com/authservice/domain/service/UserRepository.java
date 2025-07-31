package com.authservice.domain.service;

import com.authservice.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    void save(User user);
}
