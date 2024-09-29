package com.example.marinepath.repository;

import com.example.marinepath.entity.Company;
import com.example.marinepath.entity.Enum.CompanyStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    List<Company> findByStatusNot(CompanyStatusEnum companyStatusEnum);
}
