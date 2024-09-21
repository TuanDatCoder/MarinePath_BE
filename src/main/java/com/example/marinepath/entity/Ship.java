package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.Ship.ShipStatusEnum;
import com.example.marinepath.entity.Enum.Ship.ShipTypeEnum;
import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ship")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "ship_code", unique = true, nullable = false)
    private String shipCode;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "capacity",nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ShipStatusEnum status;

    @Column(name = "build_year",nullable = false)
    private Integer buildYear;

    @Column(name = "flag",nullable = false)
    private String flag;

    @Enumerated(EnumType.STRING)
    @Column(name = "type",nullable = false)
    private ShipTypeEnum type;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;

}
