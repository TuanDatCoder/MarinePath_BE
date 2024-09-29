package com.example.marinepath.controller;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Company.CompanyRequestDTO;
import com.example.marinepath.dto.Company.CompanyResponseDTO;
import com.example.marinepath.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> createCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {
        ApiResponse<CompanyResponseDTO> response = companyService.createCompany(companyRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> updateCompany(@PathVariable Integer id, @RequestBody CompanyRequestDTO companyRequestDTO) {
        ApiResponse<CompanyResponseDTO> response = companyService.updateCompany(id, companyRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Integer id) {
        ApiResponse<Void> response = companyService.deleteCompany(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> getCompanyById(@PathVariable Integer id) {
        ApiResponse<CompanyResponseDTO> response = companyService.getCompanyById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getAllCompanies() {
        ApiResponse<List<CompanyResponseDTO>> response = companyService.getAllCompanies();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
