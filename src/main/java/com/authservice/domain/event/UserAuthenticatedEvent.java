package com.authservice.domain.event;

import java.time.Instant;

public record UserAuthenticatedEvent(String email, Instant timestamp) {
}
