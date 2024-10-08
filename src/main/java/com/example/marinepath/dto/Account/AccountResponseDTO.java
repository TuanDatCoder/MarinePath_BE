package com.example.marinepath.dto.Account;

import com.example.marinepath.entity.Enum.Account.AccountGenderEnum;
import com.example.marinepath.entity.Enum.Account.AccountRoleEnum;
import com.example.marinepath.entity.Enum.Account.AccountStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private Integer id;
    private Integer companyId;
    private String email;
    private String name;
    private AccountGenderEnum gender;
    private String picture;
    private AccountRoleEnum role;
    private AccountStatusEnum status;
    private String createdAt;

}