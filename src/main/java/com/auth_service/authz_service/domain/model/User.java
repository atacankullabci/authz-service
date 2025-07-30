package com.auth_service.authz_service.domain.model;

import lombok.Data;

import java.util.Set;

// Aggregate Root

@Data
public class User {
    private Long id;
    private String email;
    private String passwordHash;
    private Set<Role> roles;
    private boolean active;
}
