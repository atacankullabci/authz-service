package com.authservice.infrastructure.security;

import com.authservice.domain.repository.RefreshTokenRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRefreshTokenRepository implements RefreshTokenRepository {

    private final StringRedisTemplate redisTemplate;

    public RedisRefreshTokenRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String refreshToken, String email, long ttlMillis) {
        this.redisTemplate.opsForValue().set(refreshToken, email, ttlMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean exists(String refreshToken) {
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(refreshToken));
    }

    @Override
    public void delete(String refreshToken) {
        this.redisTemplate.delete(refreshToken);
    }
}
