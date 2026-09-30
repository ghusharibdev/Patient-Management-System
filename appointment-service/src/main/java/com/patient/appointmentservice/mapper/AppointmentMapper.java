package com.patient.appointmentservice.mapper;

import com.patient.appointmentservice.dto.AppointmentRequestDTO;
import com.patient.appointmentservice.dto.AppointmentResponseDTO;
import com.patient.appointmentservice.model.Appointment;

import java.time.LocalDate;
import java.util.UUID;

public class AppointmentMapper {
    public static AppointmentResponseDTO toDTO(Appointment appointment) {
        AppointmentResponseDTO appointmentResponseDTO = new AppointmentResponseDTO();
        appointmentResponseDTO.setId(appointment.getId().toString());
        appointmentResponseDTO.setPatientId(appointment.getPatientId().toString());
        appointmentResponseDTO.setPatientName(appointment.getPatientName());
        appointmentResponseDTO.setDoctorName(appointment.getDoctorName());
        appointmentResponseDTO.setAppointmentDate(appointment.getAppointmentDate().toString());
        appointmentResponseDTO.setReason(appointment.getReason());
        appointmentResponseDTO.setStatus(appointment.getStatus());
        return appointmentResponseDTO;
    }

    public static Appointment toModel(AppointmentRequestDTO appointmentRequestDTO, String patientName) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(UUID.fromString(appointmentRequestDTO.getPatientId()));
        appointment.setPatientName(patientName);
        appointment.setDoctorName(appointmentRequestDTO.getDoctorName());
        appointment.setAppointmentDate(LocalDate.parse(appointmentRequestDTO.getAppointmentDate()));
        appointment.setReason(appointmentRequestDTO.getReason());
        appointment.setStatus("SCHEDULED");
        return appointment;
    }
}
