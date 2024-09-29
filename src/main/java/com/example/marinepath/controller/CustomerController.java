package com.example.marinepath.controller;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Customer.CustomerRequestDTO;
import com.example.marinepath.dto.Customer.CustomerResponseDTO;
import com.example.marinepath.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        ApiResponse<CustomerResponseDTO> response = customerService.createCustomer(customerRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> updateCustomer(@PathVariable Integer id, @RequestBody CustomerRequestDTO customerRequestDTO) {
        ApiResponse<CustomerResponseDTO> response = customerService.updateCustomer(id, customerRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Integer id) {
        ApiResponse<Void> response = customerService.deleteCustomer(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCompanyById(@PathVariable Integer id) {
        ApiResponse<CustomerResponseDTO> response = customerService.getCustomerById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getAllCustomers() {
        ApiResponse<List<CustomerResponseDTO>> response = customerService.getAllCustomers();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
