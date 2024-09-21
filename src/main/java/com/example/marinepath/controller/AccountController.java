package com.example.marinepath.controller;

import com.example.marinepath.dto.Account.AccountResponseDTO;
import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/current")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getCurrentAccount() {
        ApiResponse<AccountResponseDTO> response = accountService.getCurrentAccount();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}