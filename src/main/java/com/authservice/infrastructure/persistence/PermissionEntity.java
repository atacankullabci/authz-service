package com.authservice.infrastructure.persistence;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PermissionEntity {
    private String name;
}
