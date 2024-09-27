package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.PortStatusEnum;
import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "port")
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PortStatusEnum status;

    @OneToMany(mappedBy = "startPort") // Sửa lại để chỉ đến startPort
    private List<TripSegment> startTripSegments;

    @OneToMany(mappedBy = "endPort") // Sửa lại để chỉ đến endPort
    private List<TripSegment> endTripSegments;

    @OneToMany(mappedBy = "port")
    private List<Container> containers;
}