package com.authservice.domain.service;

import com.authservice.domain.event.UserAuthenticatedEvent;

public interface AuthEventPublisher {
    void publish(UserAuthenticatedEvent event);
}