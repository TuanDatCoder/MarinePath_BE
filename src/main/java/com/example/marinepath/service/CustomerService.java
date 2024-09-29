package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Customer.CustomerRequestDTO;
import com.example.marinepath.dto.Customer.CustomerResponseDTO;
import com.example.marinepath.entity.Customer;
import com.example.marinepath.entity.Enum.Customer.CustomerStatusEnum;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;
    public ApiResponse<CustomerResponseDTO> createCustomer(CustomerRequestDTO customerRequestDTO) {
        try {
            Customer customer = new Customer();
            customer.setName(customerRequestDTO.getName());
            customer.setEmail(customerRequestDTO.getEmail());
            customer.setGender(customerRequestDTO.getGender());
            customer.setRole(customerRequestDTO.getRole());
            customer.setStatus(customerRequestDTO.getStatus());
            customer.setCreatedAt(LocalDateTime.now());

            Customer savedCustomer = customerRepository.save(customer);
            CustomerResponseDTO responseDTO = convertToDto(savedCustomer);
            return new ApiResponse<>(201, "Company created successfully", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating company: " + e.getMessage(), null);
        }
    }

    private CustomerResponseDTO convertToDto(Customer customer) {
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO = objectMapper.convertValue(customer, CustomerResponseDTO.class);
        return responseDTO;
    }

    public ApiResponse<CustomerResponseDTO> getCustomerById(Integer id) {
        try {
            Customer customer  = customerRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CUSTOMER_NOT_FOUND));
            if (customer.getStatus() == CustomerStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.CUSTOMER_DELETED);
            }
            CustomerResponseDTO responseDTO = convertToDto(customer);
            return new ApiResponse<>(200, "Customer found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving customer: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<CustomerResponseDTO>> getAllCustomers() {
        try {
            List<Customer> customers = customerRepository.findByStatusNot(CustomerStatusEnum.DELETED);
            List<CustomerResponseDTO> responseDTOs = customers.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Customers retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving customers: " + e.getMessage(), null);
        }
    }

    public ApiResponse<CustomerResponseDTO> updateCustomer(Integer id, CustomerRequestDTO customerRequestDTO) {
        try {
            Customer existingCustomer = customerRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CUSTOMER_NOT_FOUND));
            if (existingCustomer.getStatus() == CustomerStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.CUSTOMER_DELETED);
            }
            existingCustomer.setName(customerRequestDTO.getName());
            existingCustomer.setEmail(customerRequestDTO.getEmail());
            existingCustomer.setGender(customerRequestDTO.getGender());
            existingCustomer.setRole(customerRequestDTO.getRole());
            existingCustomer.setStatus(customerRequestDTO.getStatus());

            Customer updatedCustomer = customerRepository.save(existingCustomer);
            CustomerResponseDTO responseDTO = convertToDto(updatedCustomer);
            return new ApiResponse<>(200, "Customer updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating customer: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deleteCustomer(Integer id) {
        try {
            Customer customer  = customerRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CUSTOMER_NOT_FOUND));
            customerRepository.deleteById(id);
            return new ApiResponse<>(204, "Customer deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting customer: " + e.getMessage(), null);
        }
    }
}
