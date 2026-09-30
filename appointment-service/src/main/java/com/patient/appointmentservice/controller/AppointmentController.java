package com.patient.appointmentservice.controller;

import com.patient.appointmentservice.dto.AppointmentRequestDTO;
import com.patient.appointmentservice.dto.AppointmentResponseDTO;
import com.patient.appointmentservice.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointment", description = "An appointment management API")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    @Operation(summary = "Get all appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments() {
        return ResponseEntity.ok().body(appointmentService.getAppointments());
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get appointments for a patient")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatient(@PathVariable UUID patientId) {
        return ResponseEntity.ok().body(appointmentService.getAppointmentsByPatient(patientId));
    }

    @PostMapping
    @Operation(summary = "Book an appointment")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        return ResponseEntity.ok().body(appointmentService.createAppointment(appointmentRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel an appointment")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
