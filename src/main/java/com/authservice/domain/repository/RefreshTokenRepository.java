package com.authservice.domain.repository;


public interface RefreshTokenRepository {
    void save(String refreshToken, String email, long ttlMillis);

    boolean exists(String refreshToken);

    void delete(String refreshToken);
}
