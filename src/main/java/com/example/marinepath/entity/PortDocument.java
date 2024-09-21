package com.example.marinepath.entity;

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

    @ManyToMany
    @JoinTable(
            name = "port_document_container",
            joinColumns = @JoinColumn(name = "port_document_id"),
            inverseJoinColumns = @JoinColumn(name = "container_id")
    )
    private Set<Container> containers = new HashSet<>();


    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

}