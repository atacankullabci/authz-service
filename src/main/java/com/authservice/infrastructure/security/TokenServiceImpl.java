package com.authservice.infrastructure.security;

import com.authservice.domain.model.Role;
import com.authservice.domain.model.User;
import com.authservice.domain.repository.RefreshTokenRepository;
import com.authservice.domain.service.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    private final Key key;
    private final long accessTokenValidityMs;
    private final long refreshTokenValidityMs;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenServiceImpl(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-token-validity-ms}") long accessTokenValidityMs,
            @Value("${security.jwt.refresh-token-validity-ms}") long refreshTokenValidityMs, RefreshTokenRepository refreshTokenRepository
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidityMs = accessTokenValidityMs;
        this.refreshTokenValidityMs = refreshTokenValidityMs;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public String generateAccessToken(User user) {
        return buildToken(user, accessTokenValidityMs);
    }

    @Override
    public String generateRefreshToken(User user) {
        String token = buildToken(user, refreshTokenValidityMs);
        refreshTokenRepository.save(token, user.getEmail(), refreshTokenValidityMs);
        return token;
    }

    @Override
    public void revokeRefreshToken(String token) {
        refreshTokenRepository.delete(token);
    }

    @Override
    public boolean isRefreshTokenValid(String token) {
        return refreshTokenRepository.exists(token);
    }

    @Override
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private String buildToken(User user, long expiry) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
