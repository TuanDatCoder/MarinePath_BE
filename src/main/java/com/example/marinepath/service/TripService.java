package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Trip.TripRequestDTO;
import com.example.marinepath.dto.Trip.TripResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.marinepath.entity.Trip;
import com.example.marinepath.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<TripResponseDTO> createTrip(TripRequestDTO tripRequestDTO) {
        try {
            Trip trip = objectMapper.convertValue(tripRequestDTO, Trip.class);
            trip.setCreatedAt(LocalDateTime.now());
            trip.setUpdatedAt(LocalDateTime.now());
            Trip savedTrip = tripRepository.save(trip);
            TripResponseDTO responseDTO = convertToDto(savedTrip);
            return new ApiResponse<>(201, "Trip created successfully", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating trip: " + e.getMessage(), null);
        }
    }

    public ApiResponse<TripResponseDTO> updateTrip(Integer id, TripRequestDTO tripRequestDTO) {
        try {
            Trip existingTrip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));
            objectMapper.updateValue(existingTrip, tripRequestDTO);
            existingTrip.setUpdatedAt(LocalDateTime.now());
            Trip updatedTrip = tripRepository.save(existingTrip);
            TripResponseDTO responseDTO = convertToDto(updatedTrip);
            return new ApiResponse<>(200, "Trip updated successfully", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating trip: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deleteTrip(Integer id) {
        try {
            tripRepository.deleteById(id);
            return new ApiResponse<>(204, "Trip deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting trip: " + e.getMessage(), null);
        }
    }

    public ApiResponse<TripResponseDTO> getTripById(Integer id) {
        try {
            Trip trip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));
            TripResponseDTO responseDTO = convertToDto(trip);
            return new ApiResponse<>(200, "Trip found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving trip: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<TripResponseDTO>> getAllTrips() {
        try {
            List<Trip> trips = tripRepository.findAll();
            List<TripResponseDTO> responseDTOs = trips.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Trips retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving trips: " + e.getMessage(), null);
        }
    }

    private TripResponseDTO convertToDto(Trip trip) {
        return objectMapper.convertValue(trip, TripResponseDTO.class);
    }
}
