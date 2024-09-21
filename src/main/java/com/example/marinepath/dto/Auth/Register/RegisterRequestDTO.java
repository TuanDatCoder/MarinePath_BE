package com.example.marinepath.dto.Auth.Register;

import com.example.marinepath.entity.Enum.Account.AccountGenderEnum;
import com.example.marinepath.entity.Enum.Account.AccountRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String email;
    private String name;
    private AccountGenderEnum gender;
    private String password;
    private AccountRoleEnum role;

}