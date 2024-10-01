package com.example.marinepath.controller;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.IncidentReport.IncidentReportRequestDTO;
import com.example.marinepath.dto.IncidentReport.IncidentReportResponseDTO;
import com.example.marinepath.service.IncidentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incident-report")
public class IncidentReportController {
    @Autowired
    private IncidentReportService incidentReportService;

    @PostMapping
    public ResponseEntity<ApiResponse<IncidentReportResponseDTO>> createIncidentReport(@RequestBody IncidentReportRequestDTO incidentReportRequestDTO) {
        ApiResponse<IncidentReportResponseDTO> response = incidentReportService.createIncidentReport(incidentReportRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<IncidentReportResponseDTO>> updateIncidentReport(@PathVariable Integer id, @RequestBody IncidentReportRequestDTO incidentReportRequestDTO) {
        ApiResponse<IncidentReportResponseDTO> response = incidentReportService.updateIncidentReport(id, incidentReportRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteIncidentReport(@PathVariable Integer id) {
        ApiResponse<Void> response = incidentReportService.deleteIncidentReport(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<IncidentReportResponseDTO>> getIncidentReportById(@PathVariable Integer id) {
        ApiResponse<IncidentReportResponseDTO> response = incidentReportService.getIncidentReportById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<IncidentReportResponseDTO>>> getAllIncidentReports() {
        ApiResponse<List<IncidentReportResponseDTO>> response = incidentReportService.getAllIncidentReports();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
