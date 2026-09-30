package com.patient.appointmentservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.json.JsonMapper;

import java.util.Optional;

@Component
public class PatientClient {
    private final RestClient restClient;
    private final JsonMapper jsonMapper;

    public PatientClient(@Value("${patient.service.url}") String patientServiceUrl, JsonMapper jsonMapper) {
        this.restClient = RestClient.create(patientServiceUrl);
        this.jsonMapper = jsonMapper;
    }

    public Optional<PatientDTO> getPatient(String patientId) {
        try {
            String body = restClient.get()
                    .uri("/patients/{id}", patientId)
                    .retrieve()
                    .body(String.class);
            return Optional.of(jsonMapper.readValue(body, PatientDTO.class));
        } catch (HttpClientErrorException.NotFound notFound) {
            return Optional.empty();
        } catch (RestClientException exception) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "patient-service is not reachable: " + exception.getMessage());
        }
    }
}
