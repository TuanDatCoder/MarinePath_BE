package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.CompanyStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "contact",nullable = false)
    private String contact;

    @Column(name = "address",nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private CompanyStatusEnum status;

    @OneToMany(mappedBy = "company")
    private List<Account> accounts;

    @OneToMany(mappedBy = "company")
    private List<Ship> ships;

    @OneToMany(mappedBy = "company")
    private List<Trip> trips;

}
