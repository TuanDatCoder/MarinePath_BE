package com.example.marinepath.dto.Customer;

import com.example.marinepath.entity.Enum.Customer.CustomerGenderEnum;
import com.example.marinepath.entity.Enum.Customer.CustomerRoleEnum;
import com.example.marinepath.entity.Enum.Customer.CustomerStatusEnum;
import lombok.Data;


@Data
public class CustomerRequestDTO {
    private String email;
    private String name;
    private CustomerGenderEnum gender;
    private CustomerRoleEnum role;
    private CustomerStatusEnum status;
}
