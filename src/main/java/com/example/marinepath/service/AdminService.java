package com.example.marinepath.service;

import com.example.marinepath.dto.Account.AccountResponseDTO;
import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.entity.Account;
import com.example.marinepath.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<List<AccountResponseDTO>> getAllAccounts() {
        try {
            List<Account> accounts = accountRepository.findAll();
            List<AccountResponseDTO> responseDTOs = accounts.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Accounts retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving accounts: " + e.getMessage(), null);
        }
    }


    private AccountResponseDTO convertToDto(Account account) {
        AccountResponseDTO responseDTO = objectMapper.convertValue(account, AccountResponseDTO.class);

        if (account.getCompany()!=null) responseDTO.setCompanyId(account.getCompany().getId());

        return responseDTO;
    }


}