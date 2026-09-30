package com.patient.patientservice.event;

import com.patient.patientservice.model.Patient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Component
public class PatientEventProducer {
    private static final String TOPIC = "patient-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonMapper jsonMapper;

    public PatientEventProducer(KafkaTemplate<String, String> kafkaTemplate, JsonMapper jsonMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = jsonMapper;
    }

    public void publishPatientCreated(Patient patient) {
        PatientEvent event = new PatientEvent(
                "PATIENT_CREATED",
                patient.getId().toString(),
                patient.getName(),
                patient.getEmail()
        );
        publish(event, patient.getId().toString());
    }

    private void publish(PatientEvent event, String key) {
        try {
            String payload = jsonMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, key, payload);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to serialise patient event", exception);
        }
    }
}
