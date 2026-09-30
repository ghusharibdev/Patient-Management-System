package com.patient.notificationservice.event;

import com.patient.notificationservice.dto.NotificationResponseDTO;
import com.patient.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

@Component
public class NotificationConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationService notificationService;
    private final JsonMapper jsonMapper;

    public NotificationConsumer(NotificationService notificationService, JsonMapper jsonMapper) {
        this.notificationService = notificationService;
        this.jsonMapper = jsonMapper;
    }

    @KafkaListener(topics = {"patient-events", "appointment-events", "billing-events"})
    public void onEvent(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            JsonNode json = jsonMapper.readTree(message);
            String eventType = json.path("eventType").asString("UNKNOWN");

            NotificationResponseDTO notification = notificationService.createNotification(eventType, topic, message);
            log.info("Notification [{}] stored from topic {}: {}", notification.getEventType(), topic, message);
        } catch (Exception exception) {
            log.error("Failed to process event from topic {}: {}", topic, message, exception);
        }
    }
}
