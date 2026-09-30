package com.patient.billingservice.event;

public record PatientEvent(String eventType, String patientId, String name, String email) {
}
