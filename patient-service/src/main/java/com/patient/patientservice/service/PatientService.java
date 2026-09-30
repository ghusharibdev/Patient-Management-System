package com.patient.patientservice.service;

import com.patient.patientservice.dto.PatientRequestDTO;
import com.patient.patientservice.dto.PatientResponseDTO;
import com.patient.patientservice.event.PatientEventProducer;
import com.patient.patientservice.mapper.PatientMapper;
import com.patient.patientservice.model.Patient;
import com.patient.patientservice.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientEventProducer patientEventProducer;

    public PatientService(PatientRepository patientRepository, PatientEventProducer patientEventProducer) {
        this.patientRepository = patientRepository;
        this.patientEventProducer = patientEventProducer;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO getPatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found: " + id));
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
                patientEventProducer.publishPatientCreated(newPatient);
        return PatientMapper.toDTO(newPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
