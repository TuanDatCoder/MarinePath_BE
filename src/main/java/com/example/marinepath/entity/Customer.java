package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.Customer.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender",nullable = true)
    private CustomerGenderEnum gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private CustomerPositionEnum role;


    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatusEnum status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;


}
