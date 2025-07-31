package com.authservice.infrastructure.persistence;

import jakarta.persistence.Embeddable;

@Embeddable
public class PermissionEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
