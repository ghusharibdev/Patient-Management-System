package com.patient.billingservice.controller;

import com.patient.billingservice.dto.BillingAccountResponseDTO;
import com.patient.billingservice.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/billing-accounts")
public class BillingController {
    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    public ResponseEntity<List<BillingAccountResponseDTO>> getBillingAccounts() {
        return ResponseEntity.ok().body(billingService.getBillingAccounts());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<BillingAccountResponseDTO> getBillingAccountByPatientId(@PathVariable String patientId) {
        return ResponseEntity.ok().body(billingService.getBillingAccountByPatientId(patientId));
    }
}
