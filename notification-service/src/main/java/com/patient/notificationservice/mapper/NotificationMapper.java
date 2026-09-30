package com.patient.notificationservice.mapper;

import com.patient.notificationservice.dto.NotificationResponseDTO;
import com.patient.notificationservice.model.Notification;

public class NotificationMapper {
    public static NotificationResponseDTO toDTO(Notification notification) {
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO();
        notificationResponseDTO.setId(notification.getId().toString());
        notificationResponseDTO.setEventType(notification.getEventType());
        notificationResponseDTO.setTopic(notification.getTopic());
        notificationResponseDTO.setMessage(notification.getMessage());
        notificationResponseDTO.setTimestamp(notification.getTimestamp().toString());
        return notificationResponseDTO;
    }
}
