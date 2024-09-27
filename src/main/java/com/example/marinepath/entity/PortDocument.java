package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.PortDocumentStatusEnum;
import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "port_document")
public class PortDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "trip_segment_id", nullable = false)
    private TripSegment tripSegment;

    @ManyToOne
    @JoinColumn(name = "port_id", nullable = false)
    private Port port;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private PortDocumentStatusEnum status;


}