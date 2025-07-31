package com.authservice.domain.model;

import java.util.Set;

// Entity

public class Role {
    private Long id;
    private String name;
    private Set<Permission> permissions;

    public Role(Long id, String name, Set<Permission> permissions) {
        this.id = id;
        this.name = name.toUpperCase();
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
