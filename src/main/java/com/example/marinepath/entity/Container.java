package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.ContainerStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "container")
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "port_id", nullable = false)
    private Port port;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToMany(mappedBy = "containers")
    private Set<PortDocument> portDocuments;

    @Column(name = "weight",nullable = false)
    private Float weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ContainerStatusEnum status;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;
}
