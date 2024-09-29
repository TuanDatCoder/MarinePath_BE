package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Company.CompanyRequestDTO;
import com.example.marinepath.dto.Company.CompanyResponseDTO;
import com.example.marinepath.entity.Company;
import com.example.marinepath.entity.Enum.CompanyStatusEnum;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    //create company
    public ApiResponse<CompanyResponseDTO> createCompany(CompanyRequestDTO companyRequestDTO) {
        try {
            Company company = new Company();
            company.setAddress(companyRequestDTO.getAddress());
            company.setContact(companyRequestDTO.getContact());
            company.setName(companyRequestDTO.getName());
            company.setStatus(companyRequestDTO.getStatus());

            Company savedCompany = companyRepository.save(company);
            CompanyResponseDTO responseDTO = convertToDto(savedCompany);
            return new ApiResponse<>(201, "Company created successfully", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating company: " + e.getMessage(), null);
        }
    }

    //View all Companies
    public ApiResponse<List<CompanyResponseDTO>> getAllCompanies() {
        try {
            List<Company> companies = companyRepository.findByStatusNot(CompanyStatusEnum.DELETED);
            List<CompanyResponseDTO> responseDTOs = companies.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Companies retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving companies: " + e.getMessage(), null);
        }
    }

    //View details
    public ApiResponse<CompanyResponseDTO> getCompanyById(Integer id) {
        try {
            Company company  = companyRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.COMPANY_NOT_FOUND));
            if (company.getStatus() == CompanyStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.COMPANY_DELETED);
            }
            CompanyResponseDTO responseDTO = convertToDto(company);
            return new ApiResponse<>(200, "Company found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving company: " + e.getMessage(), null);
        }
    }

    private CompanyResponseDTO convertToDto(Company company) {
        CompanyResponseDTO responseDTO = new CompanyResponseDTO();
        responseDTO = objectMapper.convertValue(company, CompanyResponseDTO.class);
        return responseDTO;
    }

    //Update company
    public ApiResponse<CompanyResponseDTO> updateCompany(Integer id, CompanyRequestDTO companyRequestDTO) {
        try {
            Company existingCompany = companyRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.COMPANY_NOT_FOUND));
            if (existingCompany.getStatus() == CompanyStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.TRIP_DELETED);
            }
                existingCompany.setName(companyRequestDTO.getName());
                existingCompany.setContact(companyRequestDTO.getContact());
                existingCompany.setAddress(companyRequestDTO.getAddress());
                existingCompany.setStatus(companyRequestDTO.getStatus());

            Company updatedTrip = companyRepository.save(existingCompany);
            CompanyResponseDTO responseDTO = convertToDto(updatedTrip);
            return new ApiResponse<>(200, "Company updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating company: " + e.getMessage(), null);
        }
    }

    //Delete company
    public ApiResponse<Void> deleteCompany(Integer id) {
        try {
            Company company  = companyRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.COMPANY_NOT_FOUND));
            companyRepository.deleteById(id);
            return new ApiResponse<>(204, "Company deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting company: " + e.getMessage(), null);
        }
    }
}
