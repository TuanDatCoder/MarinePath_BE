package com.example.marinepath.controller;

import com.example.marinepath.dto.Trip.TripRequestDTO;
import com.example.marinepath.dto.Trip.TripResponseDTO;
import com.example.marinepath.service.TripService;
import org.springframework.web.bind.annotation.*;
import com.example.marinepath.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api/trips")
public class TripController {
    @Autowired
    private TripService tripService;

    @PostMapping
    public ResponseEntity<ApiResponse<TripResponseDTO>> createTrip(@RequestBody TripRequestDTO tripRequestDTO) {
        ApiResponse<TripResponseDTO> response = tripService.createTrip(tripRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TripResponseDTO>> updateTrip(@PathVariable Integer id, @RequestBody TripRequestDTO tripRequestDTO) {
        ApiResponse<TripResponseDTO> response = tripService.updateTrip(id, tripRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTrip(@PathVariable Integer id) {
        ApiResponse<Void> response = tripService.deleteTrip(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TripResponseDTO>> getTripById(@PathVariable Integer id) {
        ApiResponse<TripResponseDTO> response = tripService.getTripById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TripResponseDTO>>> getAllTrips() {
        ApiResponse<List<TripResponseDTO>> response = tripService.getAllTrips();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
