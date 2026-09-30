package com.patient.notificationservice.service;

import com.patient.notificationservice.dto.NotificationResponseDTO;
import com.patient.notificationservice.mapper.NotificationMapper;
import com.patient.notificationservice.model.Notification;
import com.patient.notificationservice.repository.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationResponseDTO createNotification(String eventType, String topic, String message) {
        Notification notification = new Notification();
        notification.setEventType(eventType);
        notification.setTopic(topic);
        notification.setMessage(message);

        Notification saved = notificationRepository.save(notification);
        return NotificationMapper.toDTO(saved);
    }

    public List<NotificationResponseDTO> getNotifications() {
        return notificationRepository.findAllByOrderByTimestampDesc()
                .stream()
                .map(NotificationMapper::toDTO)
                .toList();
    }

    public NotificationResponseDTO getNotification(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found: " + id));
        return NotificationMapper.toDTO(notification);
    }
}
