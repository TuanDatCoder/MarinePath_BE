package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.IncidentReport.IncidentReportRequestDTO;
import com.example.marinepath.dto.IncidentReport.IncidentReportResponseDTO;
import com.example.marinepath.entity.*;
import com.example.marinepath.entity.Enum.IncidentReportStatusEnum;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.IncidentReportRepository;
import com.example.marinepath.repository.TripSegmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidentReportService {
    @Autowired
    private IncidentReportRepository incidentReportRepository;

    @Autowired
    private TripSegmentRepository tripSegmentRepository;

    @Autowired
    private ObjectMapper objectMapper;
    public ApiResponse<IncidentReportResponseDTO> createIncidentReport(IncidentReportRequestDTO incidentReportRequestDTO) {
        try {
            TripSegment tripSegment = tripSegmentRepository.findById(incidentReportRequestDTO.getTripSegmentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_SEGMENT_NOT_FOUND));

            IncidentReport incidentReport = new IncidentReport();
            incidentReport.setTripSegment(tripSegment);
            incidentReport.setDescription(incidentReportRequestDTO.getDescription());
            incidentReport.setReportedAt(LocalDateTime.now());
            incidentReport.setStatus(IncidentReportStatusEnum.PENDING);

            IncidentReport savedIncidentReport = incidentReportRepository.save(incidentReport);
            IncidentReportResponseDTO responseDTO = convertToDto(savedIncidentReport);
            return new ApiResponse<>(201, "Incident Report created successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating Incident Report: " + e.getMessage(), null);
        }
    }

    private IncidentReportResponseDTO convertToDto(IncidentReport incidentReport) {
        IncidentReportResponseDTO responseDTO =  objectMapper.convertValue(incidentReport, IncidentReportResponseDTO.class);
        if(incidentReport.getTripSegment()!=null) {
            responseDTO.setTripSegmentId(incidentReport.getTripSegment().getId());
        }
        return responseDTO;
    }

    public ApiResponse<List<IncidentReportResponseDTO>> getAllIncidentReports() {
        try {
            List<IncidentReport> incidentReports = incidentReportRepository.findAll();
            List<IncidentReportResponseDTO> responseDTOs = incidentReports.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Incident Reports successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving Incident Reports: " + e.getMessage(), null);
        }
    }

    public ApiResponse<IncidentReportResponseDTO> updateIncidentReport(Integer id, IncidentReportRequestDTO incidentReportRequestDTO) {
        try {
            IncidentReport incidentReport = incidentReportRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.INCIDENT_REPORT_NOT_FOUND));

            TripSegment tripSegment = tripSegmentRepository.findById(incidentReportRequestDTO.getTripSegmentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_SEGMENT_NOT_FOUND));

            incidentReport.setTripSegment(tripSegment);
            incidentReport.setDescription(incidentReportRequestDTO.getDescription());
            incidentReport.setReportedAt(LocalDateTime.now());

            IncidentReport updatedIncidentReport = incidentReportRepository.save(incidentReport);
            IncidentReportResponseDTO responseDTO = convertToDto(updatedIncidentReport);
            return new ApiResponse<>(200, "Incident Report updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating Incident Report: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deleteIncidentReport(Integer id) {
        try {
            IncidentReport incidentReport = incidentReportRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.INCIDENT_REPORT_NOT_FOUND));

            incidentReportRepository.delete(incidentReport);
            return new ApiResponse<>(204, "Incident Report deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting Incident Report: " + e.getMessage(), null);
        }
    }

    public ApiResponse<IncidentReportResponseDTO> getIncidentReportById(Integer id) {
        try {
            IncidentReport incidentReport = incidentReportRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.INCIDENT_REPORT_NOT_FOUND));

            IncidentReportResponseDTO responseDTO = convertToDto(incidentReport);
            return new ApiResponse<>(200, "Incident Report found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving Incident Report: " + e.getMessage(), null);
        }
    }
}
