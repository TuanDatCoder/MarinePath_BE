package com.example.marinepath.entity;


import com.example.marinepath.entity.Enum.Staff.*;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import lombok.*;


import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff")
public class Staff{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Email
    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "gender",nullable = true)
    @Enumerated(EnumType.STRING)
    private StaffGenderEnum gender;

    @Column(name = "picture",nullable = false)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "position",nullable = false)
    private StaffRoleEnum position;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StaffStatusEnum status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;
}
