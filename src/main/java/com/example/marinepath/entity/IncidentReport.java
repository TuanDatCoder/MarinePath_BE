package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.IncidentReportStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "incident_report")
public class IncidentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "trip_segment_id")
    private TripSegment tripSegment;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "reported_at",nullable = false)
    private LocalDateTime reportedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private IncidentReportStatusEnum status;

}
