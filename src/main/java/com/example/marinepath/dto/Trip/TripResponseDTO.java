package com.example.marinepath.dto.Trip;

import com.example.marinepath.entity.Enum.Trip.TripStatusEnum;

import java.time.LocalDateTime;

public class TripResponseDTO {
    private Integer id;
    private Integer shipId;
    private Integer accountId;
    private Integer companyId;
    private Float weight;
    private String description;
    private Integer containerCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TripStatusEnum status;
}
