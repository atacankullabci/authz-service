package com.authservice.domain.service;

import com.authservice.domain.model.User;

public interface TokenService {
    String generateAccessToken(User user);

    String generateRefreshToken(User user);
}
