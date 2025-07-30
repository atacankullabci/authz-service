package com.auth_service.authz_service.domain.model;

import lombok.Data;

import java.util.Set;

// Entity

@Data
public class Role {
    private Long id;
    private String name;
    private Set<Permission> permissions;
}
