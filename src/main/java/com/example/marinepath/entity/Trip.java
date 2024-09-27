package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.Trip.TripStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ship_id", nullable = false)
    private Ship ship;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @Column(name = "weight",nullable = false)
    private Float weight;

    @Column(name = "desciption",nullable = false)
    private String description;

    @Column(name = "container_count",nullable = false)
    private Integer containerCount;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private TripStatusEnum status;

    @OneToMany(mappedBy = "trip")
    private List<TripSegment> tripSegments;

    @OneToMany(mappedBy = "trip")
    private List<Order> orders;

}
