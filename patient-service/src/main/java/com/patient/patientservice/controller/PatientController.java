package com.patient.patientservice.controller;

import com.patient.patientservice.dto.PatientRequestDTO;
import com.patient.patientservice.dto.PatientResponseDTO;
import com.patient.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "A patient management API")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get all patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
                List<PatientResponseDTO> patients = patientService.getPatients();
                return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Add patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a patient by id")
    public ResponseEntity<PatientResponseDTO> getPatient(@PathVariable UUID id) {
        return ResponseEntity.ok().body(patientService.getPatient(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}

