package com.example.marinepath.controller;

import com.example.marinepath.dto.Account.AccountResponseDTO;
import com.example.marinepath.dto.Account.AccountUpdateResponseDTO;
import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PutMapping
    public ResponseEntity<ApiResponse<AccountResponseDTO>> updateAccount(@ModelAttribute AccountUpdateResponseDTO accountUpdateResponseDTO,
                                                                         @RequestParam("file") MultipartFile file) {
        ApiResponse<AccountResponseDTO> response = accountService.updateAccount(accountUpdateResponseDTO, file);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Integer id) {
        ApiResponse<Void> response = accountService.deleteAccount(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getAccountById(@PathVariable Integer id) {
        ApiResponse<AccountResponseDTO> response = accountService.getAccountById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getAccountByEmail(@PathVariable String email) {
        ApiResponse<AccountResponseDTO> response = accountService.getAccountByEmail2(email);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> updateProfile(@RequestBody AccountUpdateResponseDTO accountUpdateResponseDTO) {
        ApiResponse<AccountResponseDTO> response = accountService.updateProfile(accountUpdateResponseDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/avatar")
    public ResponseEntity<ApiResponse<String>> updateAvatar(@RequestParam("file") MultipartFile file) {
        ApiResponse<String> response = accountService.updateAvatar(file);
        return ResponseEntity.status(response.getCode()).body(response);
    }



}