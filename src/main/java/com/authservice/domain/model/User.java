package com.authservice.domain.model;

import java.util.Set;

// Aggregate Root

public class User {
    private Long id;
    private String email;
    private String passwordHash;
    private Set<Role> roles;
    private boolean active;

    public User(Long id, String email, String passwordHash, Set<Role> roles, boolean active) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roles = roles;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isActive() {
        return active;
    }

    public String getRolesAsString() {
        String rolesAsString = "";
        for (Role role : roles) {
            rolesAsString += role.getName() + ", ";
        }
        return rolesAsString.substring(0, rolesAsString.length() - 2);
    }

    public boolean hasPermission(String permission) {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(p -> p.getName().equalsIgnoreCase(permission));
    }
}
