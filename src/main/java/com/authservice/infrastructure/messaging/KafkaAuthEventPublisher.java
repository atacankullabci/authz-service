package com.authservice.infrastructure.messaging;

import com.authservice.domain.event.UserAuthenticatedEvent;
import com.authservice.domain.service.AuthEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaAuthEventPublisher implements AuthEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(KafkaAuthEventPublisher.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaAuthEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(UserAuthenticatedEvent event) {
        logger.info("Publishing event {}", event);
        kafkaTemplate.send("auth.events", event.email(), event);
    }
}