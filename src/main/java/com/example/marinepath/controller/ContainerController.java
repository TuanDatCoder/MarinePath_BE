package com.example.marinepath.controller;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Container.ContainerRequestDTO;
import com.example.marinepath.dto.Container.ContainerResponseDTO;
import com.example.marinepath.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/container")
public class ContainerController {
    @Autowired
    private ContainerService containerService;

    @PostMapping
    public ResponseEntity<ApiResponse<ContainerResponseDTO>> createContainer(@RequestBody ContainerRequestDTO containerRequestDTO) {
        ApiResponse<ContainerResponseDTO> response = containerService.createContainer(containerRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContainerResponseDTO>> updateContainer(@PathVariable Integer id, @RequestBody ContainerRequestDTO containerRequestDTO) {
        ApiResponse<ContainerResponseDTO> response = containerService.updateContainer(id, containerRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContainer(@PathVariable Integer id) {
        ApiResponse<Void> response = containerService.deleteContainer(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContainerResponseDTO>> getContainerById(@PathVariable Integer id) {
        ApiResponse<ContainerResponseDTO> response = containerService.getContainerById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContainerResponseDTO>>> getAllCompanies() {
        ApiResponse<List<ContainerResponseDTO>> response = containerService.getAllContainers();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
