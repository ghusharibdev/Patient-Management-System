package com.patient.patientservice.event;

public record PatientEvent(String eventType, String patientId, String name, String email) {
}
