package com.example.marinepath.entity;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "port")
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "location",nullable = false)
    private String location;

    @Column(name = "capacity",nullable = false)
    private Integer capacity;

    @Column(name = "contact_info",nullable = false)
    private String contactInfo;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;

}
