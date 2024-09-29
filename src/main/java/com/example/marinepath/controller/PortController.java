package com.example.marinepath.controller;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Port.PortRequestDTO;
import com.example.marinepath.dto.Port.PortResponseDTO;
import com.example.marinepath.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api/ports")
public class PortController {
    @Autowired
    private PortService portService;

    @PostMapping
    public ResponseEntity<ApiResponse<PortResponseDTO>> createPort(@RequestBody PortRequestDTO portRequestDTO) {
        ApiResponse<PortResponseDTO> response = portService.createPort(portRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PortResponseDTO>> updatePort(@PathVariable Integer id, @RequestBody PortRequestDTO portRequestDTO) {
        ApiResponse<PortResponseDTO> response = portService.updatePort(id, portRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePort(@PathVariable Integer id) {
        ApiResponse<Void> response = portService.deletePort(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PortResponseDTO>> getShipById(@PathVariable Integer id) {
        ApiResponse<PortResponseDTO> response = portService.getPortById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PortResponseDTO>>> getAllPorts() {
        ApiResponse<List<PortResponseDTO>> response = portService.getAllPorts();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
