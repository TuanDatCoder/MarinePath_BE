package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.TripSegment.TripSegmentRequestDTO;
import com.example.marinepath.dto.TripSegment.TripSegmentResponseDTO;
import com.example.marinepath.dto.TripSegment.TripSegmentUpdateRequestDTO;
import com.example.marinepath.entity.Enum.TripSegmentStatusEnum;
import com.example.marinepath.entity.Port;
import com.example.marinepath.entity.Trip;
import com.example.marinepath.entity.TripSegment;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.PortRepository;
import com.example.marinepath.repository.TripRepository;
import com.example.marinepath.repository.TripSegmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripSegmentService {

    @Autowired
    private TripSegmentRepository tripSegmentRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<TripSegmentResponseDTO> createTripSegment(TripSegmentRequestDTO tripSegmentRequestDTO) {
        try {
            Trip trip = tripRepository.findById(tripSegmentRequestDTO.getTripId())
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_FOUND));

            Port startPort = portRepository.findById(tripSegmentRequestDTO.getStartPortId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));

            Port endPort = portRepository.findById(tripSegmentRequestDTO.getEndPortId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));

            TripSegment tripSegment = new TripSegment();
            tripSegment.setTrip(trip);
            tripSegment.setStartPort(startPort);
            tripSegment.setEndPort(endPort);
            tripSegment.setStartDate(tripSegmentRequestDTO.getStartDate());
            tripSegment.setEndDate(tripSegmentRequestDTO.getEndDate());
            tripSegment.setStatus(TripSegmentStatusEnum.PENDING);

            TripSegment savedTripSegment = tripSegmentRepository.save(tripSegment);
            TripSegmentResponseDTO responseDTO = convertToDto(savedTripSegment);
            return new ApiResponse<>(201, "TripSegment created successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating trip segment: " + e.getMessage(), null);
        }
    }

    public ApiResponse<TripSegmentResponseDTO> updateTripSegment(Integer id, TripSegmentUpdateRequestDTO tripSegmentUpdateRequestDTO) {
        try {

            TripSegment existingTripSegment = tripSegmentRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_SEGMENT_NOT_FOUND));

            if (existingTripSegment.getStatus() == TripSegmentStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.TRIP_SEGMENT_DELETED);
            }

            Trip trip = tripRepository.findById(tripSegmentUpdateRequestDTO.getTripId())
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_FOUND));

            Port startPort = portRepository.findById(tripSegmentUpdateRequestDTO.getStartPortId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));

            Port endPort = portRepository.findById(tripSegmentUpdateRequestDTO.getEndPortId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));

            existingTripSegment.setTrip(trip);
            existingTripSegment.setStartPort(startPort);
            existingTripSegment.setEndPort(endPort);
            existingTripSegment.setStartDate(tripSegmentUpdateRequestDTO.getStartDate());
            existingTripSegment.setEndDate(tripSegmentUpdateRequestDTO.getEndDate());
            existingTripSegment.setStatus(tripSegmentUpdateRequestDTO.getStatus());

            TripSegment updatedTripSegment = tripSegmentRepository.save(existingTripSegment);
            TripSegmentResponseDTO responseDTO = convertToDto(updatedTripSegment);
            return new ApiResponse<>(200, "TripSegment updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating trip segment: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deleteTripSegment(Integer id) {
        try {
            TripSegment tripSegment = tripSegmentRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_SEGMENT_NOT_FOUND));

            tripSegmentRepository.deleteById(id);
            return new ApiResponse<>(204, "TripSegment deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting trip segment: " + e.getMessage(), null);
        }
    }

    public ApiResponse<TripSegmentResponseDTO> getTripSegmentById(Integer id) {
        try {
            TripSegment tripSegment = tripSegmentRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_SEGMENT_NOT_FOUND));

            if (tripSegment.getStatus() == TripSegmentStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.TRIP_SEGMENT_DELETED);
            }

            TripSegmentResponseDTO responseDTO = convertToDto(tripSegment);
            return new ApiResponse<>(200, "TripSegment found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving trip segment: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<TripSegmentResponseDTO>> getAllTripSegments() {
        try {
            List<TripSegment> tripSegments = tripSegmentRepository.findAll();
            List<TripSegmentResponseDTO> responseDTOs = tripSegments.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "TripSegments retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving trip segments: " + e.getMessage(), null);
        }
    }

    private TripSegmentResponseDTO convertToDto(TripSegment tripSegment) {
        TripSegmentResponseDTO responseDTO = objectMapper.convertValue(tripSegment, TripSegmentResponseDTO.class);

        if (tripSegment.getTrip() != null) {
            responseDTO.setTripId(tripSegment.getTrip().getId());
        }
        if (tripSegment.getStartPort() != null) {
            responseDTO.setStartPortId(tripSegment.getStartPort().getId());
        }
        if (tripSegment.getEndPort() != null) {
            responseDTO.setEndPortId(tripSegment.getEndPort().getId());
        }
        return responseDTO;
    }
}
