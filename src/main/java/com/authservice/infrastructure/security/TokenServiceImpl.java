package com.authservice.infrastructure.security;

import com.authservice.domain.model.Role;
import com.authservice.domain.model.User;
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

    public TokenServiceImpl(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-token-validity-ms}") long accessTokenValidityMs,
            @Value("${security.jwt.refresh-token-validity-ms}") long refreshTokenValidityMs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidityMs = accessTokenValidityMs;
        this.refreshTokenValidityMs = refreshTokenValidityMs;
    }

    @Override
    public String generateAccessToken(User user) {
        return buildToken(user, accessTokenValidityMs);
    }

    @Override
    public String generateRefreshToken(User user) {
        return buildToken(user, refreshTokenValidityMs);
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
