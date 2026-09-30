package com.patient.appointmentservice.event;

import com.patient.appointmentservice.dto.AppointmentResponseDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Component
public class AppointmentEventProducer {
    private static final String TOPIC = "appointment-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonMapper jsonMapper;

    public AppointmentEventProducer(KafkaTemplate<String, String> kafkaTemplate, JsonMapper jsonMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = jsonMapper;
    }

    public void publishAppointmentCreated(AppointmentResponseDTO appointment) {
        AppointmentEvent event = new AppointmentEvent(
                "APPOINTMENT_CREATED",
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getPatientName(),
                appointment.getDoctorName(),
                appointment.getAppointmentDate()
        );

        try {
            String payload = jsonMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, appointment.getPatientId(), payload);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to serialise appointment event", exception);
        }
    }
}
