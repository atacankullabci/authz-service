package com.authservice.infrastructure.messaging;

import com.authservice.domain.event.UserAuthenticatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuthEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(AuthEventConsumer.class);

    @KafkaListener(topics = "auth.events", groupId = "auth-service", containerFactory = "kafkaListenerContainerFactory")
    public void handleAuthEvent(UserAuthenticatedEvent event) {
        log.info("Received Auth Event: email={}, timestamp={}", event.email(), event.timestamp());
        // Future: Save to audit table, alert system, etc.
    }
}