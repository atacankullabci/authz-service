package com.auth_service.authz_service.domain.service;

import com.auth_service.authz_service.domain.model.User;

public interface TokenService {
    String generateAccessToken(User user);

    String generateRefreshToken(User user);
}
