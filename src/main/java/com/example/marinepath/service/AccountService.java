package com.example.marinepath.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.marinepath.dto.Account.AccountResponseDTO;
import com.example.marinepath.dto.Account.AccountUpdateResponseDTO;
import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Auth.Login.LoginRequestDTO;
import com.example.marinepath.dto.Auth.Login.LoginResponseDTO;
import com.example.marinepath.dto.Auth.Register.RegisterRequestDTO;
import com.example.marinepath.entity.Account;
import com.example.marinepath.entity.Company;
import com.example.marinepath.entity.Enum.Account.AccountProviderEnum;
import com.example.marinepath.entity.Enum.Account.AccountStatusEnum;
import com.example.marinepath.entity.Enum.OrderStatusEnum;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.exception.Token.InvalidToken;
import com.example.marinepath.repository.AccountRepository;
import com.example.marinepath.repository.CompanyRepository;
import com.example.marinepath.security.JwtTokenUtil;
import com.example.marinepath.utils.AccountUtils;
import com.example.marinepath.utils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountUtils accountUtils;

    @Autowired
    private UploadFileUtils uploadFileUtils;
    @Autowired
    private UrlShortenerService urlShortenerService;

    public ApiResponse<String> registerNewAccount(RegisterRequestDTO registerRequestDTO) {
        if (accountRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new ApiException("User existed", ErrorCode.USER_EXISTED);
        }

        Account account = new Account();
        account.setEmail(registerRequestDTO.getEmail());
        account.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        account.setName(registerRequestDTO.getName());
        account.setGender(registerRequestDTO.getGender());
        account.setProvider(AccountProviderEnum.LOCAL);
        account.setRole(registerRequestDTO.getRole());
        account.setStatus(AccountStatusEnum.UNVERIFIED);
        account.setCreatedAt(LocalDateTime.now());
        accountRepository.save(account);

        String verificationToken = jwtTokenUtil.generateToken(new org.springframework.security.core.userdetails.User(account.getEmail(), "", new ArrayList<>()));
        String verificationLink = "http://localhost:8080/auth/verify/" + verificationToken;
        emailService.sendVerificationEmail(account.getEmail(), account.getName(), verificationLink);

        return new ApiResponse<>(201, "Registration successful, please check your email to verify your account.", null);
    }

    public ApiResponse<String> verifyAccountByToken(String token) {
        try {
            String email = jwtTokenUtil.getEmailFromToken(token);
            verifyAccountByEmail(email);
            return new ApiResponse<>(200, "Account verification successful.", null);
        } catch (InvalidToken | TokenExpiredException e) {
            throw new ApiException("Invalid token", ErrorCode.TOKEN_INVALID);
        } catch (Exception e) {
            throw new ApiException("Error verifying account: " + e.getMessage(), ErrorCode.INTERNAL_ERROR);
        }
    }

    public void verifyAccountByEmail(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getStatus() == AccountStatusEnum.UNVERIFIED) {
                account.setStatus(AccountStatusEnum.VERIFIED);
                accountRepository.save(account);
            }
        } else {
            throw new ApiException("Account not found", ErrorCode.ACCOUNT_NOT_FOUND);
        }
    }
    public ApiResponse<String> requestPasswordReset(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found with email: " + email, ErrorCode.USER_NOT_FOUND));

        String resetToken = jwtTokenUtil.generateToken(new org.springframework.security.core.userdetails.User(email, "", new ArrayList<>()));
        String resetLink = "http://localhost:8080/auth/reset-password?token=" + resetToken;

        emailService.sendResetPasswordEmail(email, account.getName(), resetLink);
        return new ApiResponse<>(200, "Password reset link sent to your email.", null);
    }

    public ApiResponse<String> resetPassword(String token, String newPassword) {
        try {
            String email = jwtTokenUtil.getEmailFromToken(token);
            Account account = accountRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiException("User not found with email: " + email, ErrorCode.USER_NOT_FOUND));

            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);
            return new ApiResponse<>(200, "Password reset successful.", null);
        } catch (InvalidToken | TokenExpiredException e) {
            throw new ApiException("Invalid or expired token", ErrorCode.TOKEN_INVALID);
        } catch (Exception e) {
            throw new ApiException("Error resetting password: " + e.getMessage(), ErrorCode.INTERNAL_ERROR);
        }
    }

    public ApiResponse<String> logout() {
        return new ApiResponse<>(200, "Logout successful", null);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Account account = accountRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ApiException("User not found with email: " + loginRequestDTO.getEmail(), ErrorCode.USER_NOT_FOUND));

        if (account.getProvider() != AccountProviderEnum.LOCAL) {
            return new LoginResponseDTO("Please log in using Google", null, null, null);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
            );

            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginRequestDTO.getEmail());
            String accessToken = jwtTokenUtil.generateToken(userDetails);
            String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
            return new LoginResponseDTO("Login successful", null, accessToken, refreshToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid email or password", ErrorCode.USERNAME_PASSWORD_NOT_CORRECT);
        }
    }



    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


    public Account getAccountByEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        return account.orElse(null);
    }


    public ApiResponse<AccountResponseDTO> getCurrentAccount() {
        try {
            Account curAccount = accountUtils.getCurrentAccount();
            AccountResponseDTO accountResponseDTO = convertToDto(curAccount);

            return new ApiResponse<>(HttpStatus.OK.value(), "Account retrieved successfully", accountResponseDTO);

        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
    }

    public ApiResponse<AccountResponseDTO> updateAccount(AccountUpdateResponseDTO accountUpdateResponseDTO, MultipartFile file) {
        try {
            Account curAccount = accountUtils.getCurrentAccount();
            Account account = accountRepository.findById(curAccount.getId())
                    .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));
            updateProfile(accountUpdateResponseDTO);
            if (file != null && !file.isEmpty()) {
                updateAvatar(file);
            }
            AccountResponseDTO responseDTO = convertToDto(account);
            return new ApiResponse<>(200, "Account updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating account: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deleteAccount(Integer id) {
        try {
            accountRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));

            accountRepository.deleteById(id);
            return new ApiResponse<>(204, "Account deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting account: " + e.getMessage(), null);
        }
    }

    public ApiResponse<AccountResponseDTO> getAccountById(Integer id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));

            if (account.getStatus() == AccountStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.ACCOUNT_DELETED);
            }
            AccountResponseDTO responseDTO = convertToDto(account);
            return new ApiResponse<>(200, "Account found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving account: " + e.getMessage(), null);
        }
    }

    public ApiResponse<AccountResponseDTO> getAccountByEmail2(String email) {
        try {
            Account account = accountRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));

            if (account.getStatus() == AccountStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.ACCOUNT_DELETED);
            }
            AccountResponseDTO responseDTO = convertToDto(account);
            return new ApiResponse<>(200, "Account found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving account by email: " + e.getMessage(), null);
        }
    }


    public ApiResponse<AccountResponseDTO> updateProfile(AccountUpdateResponseDTO accountUpdateResponseDTO) {
        try {
            Account curAccount = accountUtils.getCurrentAccount();
            Account account = accountRepository.findById(curAccount.getId())
                    .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));

            Company company = companyRepository.findById(accountUpdateResponseDTO.getCompanyId())
                    .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));
            if (account.getStatus() == AccountStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.ACCOUNT_DELETED);
            }

            // Kiểm tra xem email có tồn tại hay không
            if (!account.getEmail().equals(accountUpdateResponseDTO.getEmail())) {
                Optional<Account> existingAccount = accountRepository.findByEmail(accountUpdateResponseDTO.getEmail());
                if (existingAccount.isPresent()) {
                    throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
                }
            }

            account.setCompany(company);
            account.setEmail(accountUpdateResponseDTO.getEmail());
            account.setName(accountUpdateResponseDTO.getName());
            account.setGender(accountUpdateResponseDTO.getGender());
            account.setStatus(accountUpdateResponseDTO.getStatus());

            Account updatedAccount = accountRepository.save(account);
            AccountResponseDTO responseDTO = convertToDto(updatedAccount);
            return new ApiResponse<>(200, "Account updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating account: " + e.getMessage(), null);
        }
    }



    public ApiResponse<String> updateAvatar(MultipartFile file) {
        try {
            Account curAccount = accountUtils.getCurrentAccount();
            Account account = accountRepository.findById(curAccount.getId())
                    .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));

            if (account.getStatus() == AccountStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.ACCOUNT_DELETED);
            }

            String fileName = uploadFileUtils.uploadFile(file);
            String url = uploadFileUtils.getSignedImageUrl(fileName);
            String shortenedUrl = urlShortenerService.shortenUrl(url);
            account.setPicture(shortenedUrl);
            accountRepository.save(account);

            return new ApiResponse<>(200, "Avatar updated successfully", shortenedUrl);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating avatar: " + e.getMessage(), null);
        }
    }


    private AccountResponseDTO convertToDto(Account account) {
        AccountResponseDTO responseDTO = objectMapper.convertValue(account, AccountResponseDTO.class);

        if (account.getCompany()!=null) responseDTO.setCompanyId(account.getCompany().getId());

        return responseDTO;
    }
}
