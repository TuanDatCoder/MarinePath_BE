package com.example.marinepath.repository;

import com.example.marinepath.entity.Customer;
import com.example.marinepath.entity.Enum.Customer.CustomerStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByStatusNot(CustomerStatusEnum customerStatusEnum);
}
