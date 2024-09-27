package com.example.marinepath.dto.Trip;

import com.example.marinepath.entity.Enum.Trip.TripStatusEnum;

import java.time.LocalDateTime;

public class TripRequestDTO {
    private Integer shipId;  // ID của tàu
    private Integer accountId; // ID của tài khoản
    private Integer companyId; // ID của công ty
    private Float weight;
    private String description;
    private Integer containerCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TripStatusEnum status;
}
