package com.example.marinepath.controller;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.PortDocument.PortDocumentRequestDTO;
import com.example.marinepath.dto.PortDocument.PortDocumentResponseDTO;
import com.example.marinepath.service.PortDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api/port-documents")
public class PortDocumentController {
    @Autowired
    private PortDocumentService portDocumentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PortDocumentResponseDTO>> createPortDocument(@RequestBody PortDocumentRequestDTO portDocumentRequestDTO) {
        ApiResponse<PortDocumentResponseDTO> response = portDocumentService.createPortDocument(portDocumentRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PortDocumentResponseDTO>> updatePortDocument(@PathVariable Integer id, @RequestBody PortDocumentRequestDTO portDocumentRequestDTO) {
        ApiResponse<PortDocumentResponseDTO> response = portDocumentService.updatePortDocument(id, portDocumentRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePortDocument(@PathVariable Integer id) {
        ApiResponse<Void> response = portDocumentService.deletePortDocument(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PortDocumentResponseDTO>> getPortDocumentById(@PathVariable Integer id) {
        ApiResponse<PortDocumentResponseDTO> response = portDocumentService.getPortDocumentById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PortDocumentResponseDTO>>> getAllPortDocuments() {
        ApiResponse<List<PortDocumentResponseDTO>> response = portDocumentService.getAllPortDocuments();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
