package com.patient.patientservice.mapper;

import com.patient.patientservice.dto.PatientRequestDTO;
import com.patient.patientservice.dto.PatientResponseDTO;
import com.patient.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setDob(patient.getDob().toString());

        return patientResponseDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDob(LocalDate.parse(patientRequestDTO.getDob()));

        return patient;
    }
}
