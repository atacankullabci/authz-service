package com.authservice.domain.model;

import lombok.Data;

import java.util.Set;

// Entity

@Data
public class Role {
    private final Long id;
    private final String name;
    private final Set<Permission> permissions;
}
