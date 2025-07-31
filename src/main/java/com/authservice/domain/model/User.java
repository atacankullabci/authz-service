package com.authservice.domain.model;

import lombok.Data;

import java.util.Set;

// Aggregate Root

@Data
public class User {
    private final Long id;
    private final String email;
    private final String passwordHash;
    private final Set<Role> roles;
    private final boolean active;
}
