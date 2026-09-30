package com.patient.billingservice.service;

import com.patient.billingservice.dto.BillingAccountResponseDTO;
import com.patient.billingservice.mapper.BillingAccountMapper;
import com.patient.billingservice.model.BillingAccount;
import com.patient.billingservice.repository.BillingAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BillingService {
    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal("100.00");

    private final BillingAccountRepository billingAccountRepository;

    public BillingService(BillingAccountRepository billingAccountRepository) {
        this.billingAccountRepository = billingAccountRepository;
    }

        public BillingAccountResponseDTO createBillingAccount(String patientId, String patientName, String email) {
        Optional<BillingAccount> existing = billingAccountRepository.findByPatientId(patientId);
        if (existing.isPresent()) {
            return BillingAccountMapper.toDTO(existing.get());
        }

        BillingAccount billingAccount = new BillingAccount();
        billingAccount.setPatientId(patientId);
        billingAccount.setPatientName(patientName);
        billingAccount.setEmail(email);
        billingAccount.setAmount(DEFAULT_AMOUNT);
        billingAccount.setStatus("ACTIVE");

        BillingAccount saved = billingAccountRepository.save(billingAccount);
        return BillingAccountMapper.toDTO(saved);
    }

    public List<BillingAccountResponseDTO> getBillingAccounts() {
        return billingAccountRepository.findAll().stream().map(BillingAccountMapper::toDTO).toList();
    }

    public BillingAccountResponseDTO getBillingAccountByPatientId(String patientId) {
        BillingAccount billingAccount = billingAccountRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No billing account for patient: " + patientId));
        return BillingAccountMapper.toDTO(billingAccount);
    }
}
