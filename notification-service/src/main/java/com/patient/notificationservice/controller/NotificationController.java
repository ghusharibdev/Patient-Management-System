package com.patient.notificationservice.controller;

import com.patient.notificationservice.dto.NotificationResponseDTO;
import com.patient.notificationservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notification", description = "A notification audit API")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @Operation(summary = "Get all notifications")
    public ResponseEntity<List<NotificationResponseDTO>> getNotifications() {
        return ResponseEntity.ok().body(notificationService.getNotifications());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a notification by id")
    public ResponseEntity<NotificationResponseDTO> getNotification(@PathVariable UUID id) {
        return ResponseEntity.ok().body(notificationService.getNotification(id));
    }
}
