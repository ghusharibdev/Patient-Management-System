package com.patient.billingservice.mapper;

import com.patient.billingservice.dto.BillingAccountResponseDTO;
import com.patient.billingservice.model.BillingAccount;

public class BillingAccountMapper {
    public static BillingAccountResponseDTO toDTO(BillingAccount billingAccount) {
        BillingAccountResponseDTO billingAccountResponseDTO = new BillingAccountResponseDTO();
        billingAccountResponseDTO.setId(billingAccount.getId().toString());
        billingAccountResponseDTO.setPatientId(billingAccount.getPatientId());
        billingAccountResponseDTO.setPatientName(billingAccount.getPatientName());
        billingAccountResponseDTO.setEmail(billingAccount.getEmail());
        billingAccountResponseDTO.setAmount(billingAccount.getAmount());
        billingAccountResponseDTO.setStatus(billingAccount.getStatus());
        return billingAccountResponseDTO;
    }
}
