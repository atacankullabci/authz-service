package com.auth_service.authz_service.domain.service;

import com.auth_service.authz_service.domain.model.User;

public interface UserRepository {
    boolean existsByEmail(String email);

    void save(User user);
}
