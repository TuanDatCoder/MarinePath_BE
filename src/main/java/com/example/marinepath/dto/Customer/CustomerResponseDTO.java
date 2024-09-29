package com.example.marinepath.dto.Customer;

import com.example.marinepath.entity.Enum.Customer.CustomerGenderEnum;
import com.example.marinepath.entity.Enum.Customer.CustomerRoleEnum;
import com.example.marinepath.entity.Enum.Customer.CustomerStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerResponseDTO {
    private Integer id;
    private String email;
    private String name;
    private CustomerGenderEnum gender;
    private CustomerRoleEnum role;
    private LocalDateTime createdAt;
    private CustomerStatusEnum status;
}
