package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Ship.ShipRequestDTO;
import com.example.marinepath.dto.Ship.ShipResponseDTO;
import com.example.marinepath.entity.Company;
import com.example.marinepath.entity.Enum.Ship.ShipStatusEnum;
import com.example.marinepath.entity.Ship;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.CompanyRepository;
import com.example.marinepath.repository.ShipRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<ShipResponseDTO> createShip(ShipRequestDTO shipRequestDTO) {
        try {
            Company company = companyRepository.findById(shipRequestDTO.getCompanyId())
                .orElseThrow(() -> new ApiException(ErrorCode.COMPANY_NOT_FOUND));

            Ship ship = new Ship();
            ship.setCompany(company);
            ship.setShipCode(shipRequestDTO.getShipCode());
            ship.setName(shipRequestDTO.getName());
            ship.setCapacity(shipRequestDTO.getCapacity());
            ship.setBuildYear(shipRequestDTO.getBuildYear());
            ship.setFlag(shipRequestDTO.getFlag());
            ship.setType(shipRequestDTO.getType());
            ship.setCreatedAt(LocalDateTime.now());
            ship.setUpdatedAt(LocalDateTime.now());
            ship.setStatus(shipRequestDTO.getStatus());

            Ship savedShip = shipRepository.save(ship);
            ShipResponseDTO responseDTO = convertToDTO(savedShip);
            return new ApiResponse<>(201, "Ship created successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating ship: " + e.getMessage(), null);
        }
    }

    public ApiResponse<ShipResponseDTO> updateShip(Integer id, ShipRequestDTO shipRequestDTO) {
        try {
            Ship existingShip = shipRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.SHIP_NOT_FOUND));
            if (existingShip.getStatus() == ShipStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.SHIP_DELETED);
            }

            Company company = companyRepository.findById(shipRequestDTO.getCompanyId())
                    .orElseThrow(() -> new ApiException(ErrorCode.COMPANY_NOT_FOUND));
            existingShip.setCompany(company);

            existingShip.setShipCode(shipRequestDTO.getShipCode());
            existingShip.setName(shipRequestDTO.getName());
            existingShip.setCapacity(shipRequestDTO.getCapacity());
            existingShip.setBuildYear(shipRequestDTO.getBuildYear());
            existingShip.setFlag(shipRequestDTO.getFlag());
            existingShip.setType(shipRequestDTO.getType());
            existingShip.setCreatedAt(LocalDateTime.now());
            existingShip.setUpdatedAt(LocalDateTime.now());
            existingShip.setStatus(shipRequestDTO.getStatus());

            Ship updatedShip = shipRepository.save(existingShip);
            ShipResponseDTO responseDTO = convertToDTO(updatedShip);
            return new ApiResponse<>(200, "Trip updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating ship: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deleteShip(Integer id) {
        try {
            shipRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.SHIP_NOT_FOUND));
            shipRepository.deleteById(id);
            return new ApiResponse<>(200, "Ship deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting ship: " + e.getMessage(), null);
        }
    }

    public ApiResponse<ShipResponseDTO> getShipById(Integer id) {
        try {
            Ship ship  = shipRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.SHIP_NOT_FOUND));
            if (ship.getStatus() == ShipStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.SHIP_DELETED);
            }
            ShipResponseDTO responseDTO = convertToDTO(ship);
            return new ApiResponse<>(200, "Ship found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving ship: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<ShipResponseDTO>> getAllShips() {
        try {
            List<Ship> ships = shipRepository.findByStatusNot(ShipStatusEnum.DELETED);
            List<ShipResponseDTO> responseDTOs = ships.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Ships retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving ships: " + e.getMessage(), null);
        }
    }

    private ShipResponseDTO convertToDTO(Ship ship) {
        ShipResponseDTO responseDTO;
        responseDTO = objectMapper.convertValue(ship, ShipResponseDTO.class);

        if (ship.getCompany() != null) {
            responseDTO.setCompanyId(ship.getCompany().getId());
        }
        return responseDTO;
    }
}
