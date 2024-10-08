package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.Container.CargoTypeEnum;
import com.example.marinepath.entity.Enum.Container.ContainerStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "container")
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "start_port_id", nullable = false)
    private Port startPort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "end_port_id", nullable = false)
    private Port endPort;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "container_receipt_id", nullable = true)
    private ContainerReceipt containerReceipt;

    @Column(name = "container_code",nullable = false)
    private String containerCode;

    @Column(name = "weight",nullable = false)
    private Float weight;

    @Column(name = "container_perator",nullable = false)
    private String containerOperator;

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo_type",nullable = false)
    private CargoTypeEnum cargoType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ContainerStatusEnum status;

}
