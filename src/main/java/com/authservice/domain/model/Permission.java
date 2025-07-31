package com.authservice.domain.model;

import java.util.Objects;

public class Permission {
    private String name;

    public Permission(String name) {
        this.name = name.toUpperCase(); // normalize
    }

    public String getName() {
        return name;
    }

    // Value object equals/hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) return false;
        Permission that = (Permission) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
