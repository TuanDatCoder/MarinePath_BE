package com.example.marinepath.dto.Ship;

import com.example.marinepath.entity.Enum.Ship.ShipStatusEnum;
import com.example.marinepath.entity.Enum.Ship.ShipTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShipResponseDTO {
    private Integer id;
    private Integer companyId;
    private String shipCode;
    private String name;
    private Integer capacity;
    private Integer buildYear;
    private String flag;
    private ShipTypeEnum type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ShipStatusEnum status;
}
