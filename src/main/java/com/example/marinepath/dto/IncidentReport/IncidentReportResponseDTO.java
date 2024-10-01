package com.example.marinepath.dto.IncidentReport;

import com.example.marinepath.entity.Enum.IncidentReportStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncidentReportResponseDTO {
    private Integer id;
    private Integer tripSegmentId;
    private String description;
    private LocalDateTime reportedAt;
    private IncidentReportStatusEnum status;
}
