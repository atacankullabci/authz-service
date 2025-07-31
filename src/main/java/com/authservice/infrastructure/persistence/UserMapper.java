package com.authservice.infrastructure.persistence;

import com.authservice.domain.model.Permission;
import com.authservice.domain.model.Role;
import com.authservice.domain.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRoles().stream()
                        .map(r -> new Role(r.getId(), r.getName(), toPermissionSet(r.getPermissions())))
                        .collect(Collectors.toSet()),
                entity.isActive()
        );
    }

    private static Set<Permission> toPermissionSet(Set<PermissionEntity> perms) {
        return perms.stream()
                .map(p -> new Permission(p.getName()))
                .collect(Collectors.toSet());
    }

    public static UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setActive(domain.isActive());
        return entity;
    }
}
