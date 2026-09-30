package com.patient.billingservice.event;

import com.patient.billingservice.dto.BillingAccountResponseDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Component
public class BillingEventProducer {
    private static final String TOPIC = "billing-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonMapper jsonMapper;

    public BillingEventProducer(KafkaTemplate<String, String> kafkaTemplate, JsonMapper jsonMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = jsonMapper;
    }

    public void publishBillingAccountCreated(BillingAccountResponseDTO account) {
        BillingEvent event = new BillingEvent(
                "BILLING_ACCOUNT_CREATED",
                account.getId(),
                account.getPatientId(),
                account.getStatus()
        );

        try {
            String payload = jsonMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, account.getPatientId(), payload);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to serialise billing event", exception);
        }
    }
}
