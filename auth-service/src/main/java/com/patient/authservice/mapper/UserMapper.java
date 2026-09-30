package com.patient.authservice.mapper;

import com.patient.authservice.dto.UserResponseDTO;
import com.patient.authservice.model.User;

public class UserMapper {
    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId().toString());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole());
        return userResponseDTO;
    }
}
