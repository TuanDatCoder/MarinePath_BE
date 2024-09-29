package com.example.marinepath.dto.TripSegment;

import com.example.marinepath.entity.Enum.TripSegmentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripSegmentRequestDTO {
    private Integer tripId;
    private Integer startPortId;
    private Integer endPortId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TripSegmentStatusEnum status;
}
