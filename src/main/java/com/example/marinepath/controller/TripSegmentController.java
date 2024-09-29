package com.example.marinepath.controller;


import com.example.marinepath.dto.TripSegment.TripSegmentRequestDTO;
import com.example.marinepath.dto.TripSegment.TripSegmentResponseDTO;
import com.example.marinepath.dto.TripSegment.TripSegmentUpdateRequestDTO;
import com.example.marinepath.service.TripSegmentService;
import com.example.marinepath.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip-segments")
public class TripSegmentController {

    @Autowired
    private TripSegmentService tripSegmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<TripSegmentResponseDTO>> createTripSegment(@RequestBody TripSegmentRequestDTO tripSegmentRequestDTO) {
        ApiResponse<TripSegmentResponseDTO> response = tripSegmentService.createTripSegment(tripSegmentRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TripSegmentResponseDTO>> updateTripSegment(@PathVariable Integer id, @RequestBody TripSegmentUpdateRequestDTO tripSegmentUpdateRequestDTO) {
        ApiResponse<TripSegmentResponseDTO> response = tripSegmentService.updateTripSegment(id, tripSegmentUpdateRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTripSegment(@PathVariable Integer id) {
        ApiResponse<Void> response = tripSegmentService.deleteTripSegment(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TripSegmentResponseDTO>> getTripSegmentById(@PathVariable Integer id) {
        ApiResponse<TripSegmentResponseDTO> response = tripSegmentService.getTripSegmentById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TripSegmentResponseDTO>>> getAllTripSegments() {
        ApiResponse<List<TripSegmentResponseDTO>> response = tripSegmentService.getAllTripSegments();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}