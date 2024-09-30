package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.PortDocument.PortDocumentRequestDTO;
import com.example.marinepath.dto.PortDocument.PortDocumentResponseDTO;
import com.example.marinepath.entity.*;
import com.example.marinepath.entity.Enum.PortDocumentStatusEnum;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.PortDocumentRepository;
import com.example.marinepath.repository.TripSegmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PortDocumentService {
    @Autowired
    private PortDocumentRepository portDocumentRepository;

    @Autowired
    private TripSegmentRepository tripSegmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<PortDocumentResponseDTO> createPortDocument(PortDocumentRequestDTO portDocumentRequestDTO) {
        try {
            TripSegment tripSegment = tripSegmentRepository.findById(portDocumentRequestDTO.getTripSegmentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_SEGMENT_NOT_FOUND));

            PortDocument portDocument = new PortDocument();
            portDocument.setTripSegment(tripSegment);
            portDocument.setName((portDocument.getName()));
            portDocument.setStatus(portDocument.getStatus());

            PortDocument savedPortDocument = portDocumentRepository.save(portDocument);
            PortDocumentResponseDTO responseDTO = convertToDTO(savedPortDocument);
            return new ApiResponse<>(201, "Port document created successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating port document: " + e.getMessage(), null);
        }
    }

    public ApiResponse<PortDocumentResponseDTO> updatePortDocument(Integer id, PortDocumentRequestDTO portDocumentRequestDTO) {
        try {
            PortDocument existingPortDocument = portDocumentRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_DOCUMENT_NOT_FOUND));
            if (existingPortDocument.getStatus() == PortDocumentStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.PORT_DOCUMENT_DELETED);
            }

            TripSegment tripSegment = tripSegmentRepository.findById(portDocumentRequestDTO.getTripSegmentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_SEGMENT_NOT_FOUND));

            existingPortDocument.setTripSegment(tripSegment);
            existingPortDocument.setName(portDocumentRequestDTO.getName());
            existingPortDocument.setStatus(portDocumentRequestDTO.getStatus());

            PortDocument updatedPortDocument = portDocumentRepository.save(existingPortDocument);
            PortDocumentResponseDTO responseDTO = convertToDTO(updatedPortDocument);
            return new ApiResponse<>(200, "Port document updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating port document: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deletePortDocument(Integer id) {
        try {
            portDocumentRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));
            portDocumentRepository.deleteById(id);
            return new ApiResponse<>(200, "Port document deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting port document: " + e.getMessage(), null);
        }
    }

    public ApiResponse<PortDocumentResponseDTO> getPortDocumentById(Integer id) {
        try {
            PortDocument portDocument  = portDocumentRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_DOCUMENT_NOT_FOUND));
            if (portDocument.getStatus() == PortDocumentStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.PORT_DOCUMENT_DELETED);
            }
            PortDocumentResponseDTO responseDTO = convertToDTO(portDocument);
            return new ApiResponse<>(200, "Port document found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving port document: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<PortDocumentResponseDTO>> getAllPortDocuments() {
        try {
            List<PortDocument> portDocuments = portDocumentRepository.findByStatusNot(PortDocumentStatusEnum.DELETED);
            List<PortDocumentResponseDTO> responseDTOs = portDocuments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Port documents retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving port documents: " + e.getMessage(), null);
        }
    }

    private PortDocumentResponseDTO convertToDTO(PortDocument portDocument) {
        PortDocumentResponseDTO responseDTO;
        responseDTO = objectMapper.convertValue(portDocument, PortDocumentResponseDTO.class);

        if (portDocument.getTripSegment() != null) {
            responseDTO.setTripSegmentId(portDocument.getTripSegment().getId());
        }
        return responseDTO;
    }

}
