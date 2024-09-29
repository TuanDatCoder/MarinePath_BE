package com.example.marinepath.dto.Port;

import com.example.marinepath.entity.Enum.PortStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PortResponseDTO {
    private Integer id;
    private String name;
    private String location;
    private Integer capacity;
    private String contactInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PortStatusEnum status;
}
