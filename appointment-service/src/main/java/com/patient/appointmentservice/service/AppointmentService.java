package com.patient.appointmentservice.service;

import com.patient.appointmentservice.client.PatientClient;
import com.patient.appointmentservice.client.PatientDTO;
import com.patient.appointmentservice.dto.AppointmentRequestDTO;
import com.patient.appointmentservice.dto.AppointmentResponseDTO;
import com.patient.appointmentservice.event.AppointmentEventProducer;
import com.patient.appointmentservice.mapper.AppointmentMapper;
import com.patient.appointmentservice.model.Appointment;
import com.patient.appointmentservice.repository.AppointmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final AppointmentEventProducer appointmentEventProducer;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientClient patientClient,
                              AppointmentEventProducer appointmentEventProducer) {
        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.appointmentEventProducer = appointmentEventProducer;
    }

    public List<AppointmentResponseDTO> getAppointments() {
        return appointmentRepository.findAll().stream().map(AppointmentMapper::toDTO).toList();
    }

    public List<AppointmentResponseDTO> getAppointmentsByPatient(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId).stream().map(AppointmentMapper::toDTO).toList();
    }

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO) {
                PatientDTO patient = patientClient.getPatient(appointmentRequestDTO.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Patient not found: " + appointmentRequestDTO.getPatientId()));

        Appointment newAppointment =
                appointmentRepository.save(AppointmentMapper.toModel(appointmentRequestDTO, patient.name()));

        AppointmentResponseDTO responseDTO = AppointmentMapper.toDTO(newAppointment);
        appointmentEventProducer.publishAppointmentCreated(responseDTO);
        return responseDTO;
    }

    public void deleteAppointment(UUID id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found: " + id);
        }
        appointmentRepository.deleteById(id);
    }
}
