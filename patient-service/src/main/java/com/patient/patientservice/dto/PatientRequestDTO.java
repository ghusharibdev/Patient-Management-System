package com.patient.patientservice.dto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {
    @NotBlank
    @Size(max = 100, message = "Name can't exceed 100 characters")
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String dob;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
