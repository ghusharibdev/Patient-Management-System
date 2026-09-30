package com.patient.billingservice.event;

public record BillingEvent(String eventType, String accountId, String patientId, String status) {
}
