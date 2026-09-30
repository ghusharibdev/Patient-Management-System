package com.patient.appointmentservice.event;

public record AppointmentEvent(String eventType,
                               String appointmentId,
                               String patientId,
                               String patientName,
                               String doctorName,
                               String appointmentDate) {
}
