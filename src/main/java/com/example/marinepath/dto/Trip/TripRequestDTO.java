package com.example.marinepath.dto.Trip;

import com.example.marinepath.entity.Enum.TripStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripRequestDTO {
    private Integer shipId;
    private Integer accountId;
    private Integer companyId;
    private Float weight;
    private String description;
    private Integer containerCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
