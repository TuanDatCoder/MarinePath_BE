package com.example.marinepath.dto.Trip;

import com.example.marinepath.entity.Enum.Trip.TripStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripResponseDTO {
    private Integer id;

    @JsonProperty("shipId")
    private Integer shipId;

    @JsonProperty("accountId")
    private Integer accountId;

    @JsonProperty("companyId")
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

