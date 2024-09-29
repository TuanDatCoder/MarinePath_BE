package com.example.marinepath.controller;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.ContainerReceipt.ContainerReceiptRequestDTO;
import com.example.marinepath.dto.ContainerReceipt.ContainerReceiptResponseDTO;
import com.example.marinepath.dto.ContainerReceipt.ContainerReceiptUpdateRequestDTO;
import com.example.marinepath.service.ContainerReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/container-receipts")
public class ContainerReceiptController {

    @Autowired
    private ContainerReceiptService containerReceiptService;

    @PostMapping
    public ResponseEntity<ApiResponse<ContainerReceiptResponseDTO>> createContainerReceipt(@RequestBody ContainerReceiptRequestDTO requestDTO) {
        ApiResponse<ContainerReceiptResponseDTO> response = containerReceiptService.createContainerReceipt(requestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContainerReceiptResponseDTO>> updateContainerReceipt(@PathVariable Integer id, @RequestBody ContainerReceiptUpdateRequestDTO requestDTO) {
        ApiResponse<ContainerReceiptResponseDTO> response = containerReceiptService.updateContainerReceipt(id, requestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContainerReceipt(@PathVariable Integer id) {
        ApiResponse<Void> response = containerReceiptService.deleteContainerReceipt(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContainerReceiptResponseDTO>> getContainerReceiptById(@PathVariable Integer id) {
        ApiResponse<ContainerReceiptResponseDTO> response = containerReceiptService.getContainerReceiptById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContainerReceiptResponseDTO>>> getAllContainerReceipts() {
        ApiResponse<List<ContainerReceiptResponseDTO>> response = containerReceiptService.getAllContainerReceipts();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
