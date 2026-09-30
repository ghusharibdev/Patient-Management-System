package com.patient.billingservice.event;

import com.patient.billingservice.dto.BillingAccountResponseDTO;
import com.patient.billingservice.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Component
public class PatientEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(PatientEventConsumer.class);
    private static final String PATIENT_CREATED = "PATIENT_CREATED";

    private final BillingService billingService;
    private final BillingEventProducer billingEventProducer;
    private final JsonMapper jsonMapper;

    public PatientEventConsumer(BillingService billingService,
                                BillingEventProducer billingEventProducer,
                                JsonMapper jsonMapper) {
        this.billingService = billingService;
        this.billingEventProducer = billingEventProducer;
        this.jsonMapper = jsonMapper;
    }

    @KafkaListener(topics = "patient-events")
    public void onPatientEvent(String message) {
        try {
            PatientEvent event = jsonMapper.readValue(message, PatientEvent.class);
            log.info("Received patient event: {}", event);

            if (PATIENT_CREATED.equals(event.eventType())) {
                BillingAccountResponseDTO account =
                        billingService.createBillingAccount(event.patientId(), event.name(), event.email());
                billingEventProducer.publishBillingAccountCreated(account);
            }
        } catch (Exception exception) {
            log.error("Failed to process patient event: {}", message, exception);
        }
    }
}
