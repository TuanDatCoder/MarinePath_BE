package com.example.marinepath.dto.Account;

import com.example.marinepath.entity.Enum.Account.AccountGenderEnum;
import com.example.marinepath.entity.Enum.Account.AccountRoleEnum;
import com.example.marinepath.entity.Enum.Account.AccountStatusEnum;
import lombok.Data;

@Data
public class AccountUpdateResponseDTO {
    private Integer companyId;
    private String email;
    private String name;
    private AccountGenderEnum gender;
    private String picture;
    private AccountStatusEnum status;
}
