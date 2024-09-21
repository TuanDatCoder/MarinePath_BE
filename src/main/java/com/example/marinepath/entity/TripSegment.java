package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.Trip.TripSegmentStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "start_port_id", nullable = false)
    private Port startPort;

    @ManyToOne
    @JoinColumn(name = "end_port_id", nullable = false)
    private Port endPort;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private TripSegmentStatusEnum status;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;
}
