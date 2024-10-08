package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.ContainerReceiptStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "container_receipt")
public class ContainerReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "port_document_id", nullable = false)
    private PortDocument portDocument;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContainerReceiptStatusEnum status;
}
