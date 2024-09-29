package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.TripSegmentStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "trip_segment")
public class TripSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "start_port_id", nullable = false)
    private Port startPort;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "end_port_id", nullable = false)
    private Port endPort;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TripSegmentStatusEnum status;

    @OneToMany(mappedBy = "tripSegment")
    private List<IncidentReport> incidentReports;

    @OneToMany(mappedBy = "tripSegment")
    private List<PortDocument> portDocuments;
}