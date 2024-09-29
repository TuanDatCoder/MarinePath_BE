package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Trip.TripRequestDTO;
import com.example.marinepath.dto.Trip.TripResponseDTO;
import com.example.marinepath.dto.Trip.TripUpdateRequestDTO;
import com.example.marinepath.entity.Account;
import com.example.marinepath.entity.Company;
import com.example.marinepath.entity.Enum.TripStatusEnum;
import com.example.marinepath.entity.Ship;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.AccountRepository;
import com.example.marinepath.repository.CompanyRepository;
import com.example.marinepath.repository.ShipRepository;
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
    private AccountRepository accountRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<TripResponseDTO> createTrip(TripRequestDTO tripRequestDTO) {
        try {
            Ship ship = shipRepository.findById(tripRequestDTO.getShipId())
                    .orElseThrow(() -> new ApiException(ErrorCode.SHIP_NOT_FOUND));

            Account account = accountRepository.findById(tripRequestDTO.getAccountId())
                    .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));

            Company company = companyRepository.findById(tripRequestDTO.getCompanyId())
                    .orElseThrow(() -> new ApiException(ErrorCode.COMPANY_NOT_FOUND));

            Trip trip = new Trip();
            trip.setShip(ship);
            trip.setAccount(account);
            trip.setCompany(company);
            trip.setWeight(tripRequestDTO.getWeight());
            trip.setDescription(tripRequestDTO.getDescription());
            trip.setContainerCount(tripRequestDTO.getContainerCount());
            trip.setStartDate(tripRequestDTO.getStartDate());
            trip.setEndDate(tripRequestDTO.getEndDate());
            trip.setStatus(TripStatusEnum.SCHEDULED);
            trip.setCreatedAt(LocalDateTime.now());
            trip.setUpdatedAt(LocalDateTime.now());

            Trip savedTrip = tripRepository.save(trip);
            TripResponseDTO responseDTO = convertToDto(savedTrip);
            return new ApiResponse<>(201, "Trip created successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating trip: " + e.getMessage(), null);
        }
    }


    public ApiResponse<TripResponseDTO> updateTrip(Integer id, TripUpdateRequestDTO tripUpdateRequestDTO) {
        try {
            Trip existingTrip = tripRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_FOUND));
            if (existingTrip.getStatus() == TripStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.TRIP_DELETED);
            }
            Ship ship = shipRepository.findById(tripUpdateRequestDTO.getShipId())
                        .orElseThrow(() -> new ApiException(ErrorCode.SHIP_NOT_FOUND));
                existingTrip.setShip(ship);

            Account account = accountRepository.findById(tripUpdateRequestDTO.getAccountId())
                        .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));
                existingTrip.setAccount(account);

            Company company = companyRepository.findById(tripUpdateRequestDTO.getCompanyId())
                        .orElseThrow(() -> new ApiException(ErrorCode.COMPANY_NOT_FOUND));
                existingTrip.setCompany(company);

            existingTrip.setWeight(tripUpdateRequestDTO.getWeight());
            existingTrip.setDescription(tripUpdateRequestDTO.getDescription());
            existingTrip.setContainerCount(tripUpdateRequestDTO.getContainerCount());
            existingTrip.setStartDate(tripUpdateRequestDTO.getStartDate());
            existingTrip.setEndDate(tripUpdateRequestDTO.getEndDate());
            existingTrip.setStatus(tripUpdateRequestDTO.getStatus());
            existingTrip.setUpdatedAt(LocalDateTime.now());

            Trip updatedTrip = tripRepository.save(existingTrip);
            TripResponseDTO responseDTO = convertToDto(updatedTrip);
            return new ApiResponse<>(200, "Trip updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating trip: " + e.getMessage(), null);
        }
    }


    public ApiResponse<Void> deleteTrip(Integer id) {
        try {
            Trip trip  = tripRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_FOUND));
            tripRepository.deleteById(id);
            return new ApiResponse<>(204, "Trip deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting trip: " + e.getMessage(), null);
        }
    }

    public ApiResponse<TripResponseDTO> getTripById(Integer id) {
        try {
            Trip trip  = tripRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_FOUND));
            if (trip.getStatus() == TripStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.TRIP_DELETED);
            }
            TripResponseDTO responseDTO = convertToDto(trip);
            return new ApiResponse<>(200, "Trip found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving trip: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<TripResponseDTO>> getAllTrips() {
        try {
            List<Trip> trips = tripRepository.findByStatusNot(TripStatusEnum.DELETED);
            List<TripResponseDTO> responseDTOs = trips.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Trips retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving trips: " + e.getMessage(), null);
        }
    }


    private TripResponseDTO convertToDto(Trip trip) {
        TripResponseDTO responseDTO = new TripResponseDTO();
        responseDTO = objectMapper.convertValue(trip, TripResponseDTO.class);

        if (trip.getShip() != null) {
            responseDTO.setShipId(trip.getShip().getId());
        }
        if (trip.getAccount() != null) {
            responseDTO.setAccountId(trip.getAccount().getId());
        }
        if (trip.getCompany() != null) {
            responseDTO.setCompanyId(trip.getCompany().getId());
        }
        return responseDTO;
    }
}
