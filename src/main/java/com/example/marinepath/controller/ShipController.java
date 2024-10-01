package com.example.marinepath.controller;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Ship.ShipRequestDTO;
import com.example.marinepath.dto.Ship.ShipResponseDTO;
import com.example.marinepath.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api/ships")
public class ShipController {
    @Autowired
    private ShipService shipService;

    @PostMapping
    public ResponseEntity<ApiResponse<ShipResponseDTO>> createShip(@RequestBody ShipRequestDTO shipRequestDTO) {
        ApiResponse<ShipResponseDTO> response = shipService.createShip(shipRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ShipResponseDTO>> updateShip(@PathVariable Integer id, @RequestBody ShipRequestDTO shipRequestDTO) {
        ApiResponse<ShipResponseDTO> response = shipService.updateShip(id, shipRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShip(@PathVariable Integer id) {
        ApiResponse<Void> response = shipService.deleteShip(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShipResponseDTO>> getShipById(@PathVariable Integer id) {
        ApiResponse<ShipResponseDTO> response = shipService.getShipById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShipResponseDTO>>> getAllShips() {
        ApiResponse<List<ShipResponseDTO>> response = shipService.getAllShips();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
