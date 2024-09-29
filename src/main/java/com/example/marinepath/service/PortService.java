package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Port.PortRequestDTO;
import com.example.marinepath.dto.Port.PortResponseDTO;
import com.example.marinepath.entity.Enum.PortStatusEnum;
import com.example.marinepath.entity.Port;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.PortRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PortService {
    @Autowired
    private PortRepository portRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<PortResponseDTO> createPort(PortRequestDTO portRequestDTO) {
        try {
            Port port = new Port();
            port.setName(portRequestDTO.getName());
            port.setLocation(portRequestDTO.getLocation());
            port.setCapacity(portRequestDTO.getCapacity());
            port.setContactInfo(portRequestDTO.getContactInfo());
            port.setCreatedAt(LocalDateTime.now());
            port.setUpdatedAt(LocalDateTime.now());
            port.setStatus(portRequestDTO.getStatus());

            Port savedPort = portRepository.save(port);
            PortResponseDTO responseDTO = convertToDTO(savedPort);
            return new ApiResponse<>(201, "Port created successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating port: " + e.getMessage(), null);
        }
    }
    public ApiResponse<PortResponseDTO> updatePort(Integer id, PortRequestDTO portRequestDTO) {
        try {
            Port existingPort = portRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));
            if (existingPort.getStatus() == PortStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.SHIP_DELETED);
            }

            existingPort.setName(portRequestDTO.getName());
            existingPort.setLocation(portRequestDTO.getLocation());
            existingPort.setCapacity(portRequestDTO.getCapacity());
            existingPort.setContactInfo(portRequestDTO.getContactInfo());
            existingPort.setCreatedAt(LocalDateTime.now());
            existingPort.setUpdatedAt(LocalDateTime.now());
            existingPort.setStatus(portRequestDTO.getStatus());

            Port updatedPort = portRepository.save(existingPort);
            PortResponseDTO responseDTO = convertToDTO(updatedPort);
            return new ApiResponse<>(200, "Port updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating port: " + e.getMessage(), null);
        }
    }
    public ApiResponse<Void> deletePort(Integer id) {
        try {
            portRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));
            portRepository.deleteById(id);
            return new ApiResponse<>(200, "Port deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting port: " + e.getMessage(), null);
        }
    }
    public ApiResponse<PortResponseDTO> getPortById(Integer id) {
        try {
            Port port = portRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));
            if (port.getStatus() == PortStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.PORT_DELETED);
            }
            PortResponseDTO responseDTO = convertToDTO(port);
            return new ApiResponse<>(200, "Port found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving port: " + e.getMessage(), null);
        }
    }
    public ApiResponse<List<PortResponseDTO>> getAllPorts() {
        try {
            List<Port> ports = portRepository.findByStatusNot(PortStatusEnum.DELETED);
            List<PortResponseDTO> responseDTOs = ports.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Ports retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving ports: " + e.getMessage(), null);
        }
    }
    private PortResponseDTO convertToDTO(Port port) {
        PortResponseDTO responseDTO;
        responseDTO = objectMapper.convertValue(port, PortResponseDTO.class);
        return responseDTO;
    }
}
