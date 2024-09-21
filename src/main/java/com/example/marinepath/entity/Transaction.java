package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "issue_at",nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "total_price",nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private TransactionStatusEnum status;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;
}
