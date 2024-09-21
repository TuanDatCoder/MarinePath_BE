package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "departure",nullable = false)
    private String departure;

    @Column(name = "arrival",nullable = false)
    private String arrival;

    @Column(name = "requestDate",nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Column(name = "payment_detail",nullable = false)
    private String PaymentDetail;

    @Column(name = "issue_at",nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expired_at",nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;
}
