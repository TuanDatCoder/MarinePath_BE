package com.example.marinepath.dto.Company;

import com.example.marinepath.entity.Enum.CompanyStatusEnum;
import lombok.Data;

@Data
public class CompanyRequestDTO {
    private String name;
    private String contact;
    private String address;
    private CompanyStatusEnum status;
}
