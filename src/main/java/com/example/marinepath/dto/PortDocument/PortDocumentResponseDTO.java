package com.example.marinepath.dto.PortDocument;

import com.example.marinepath.entity.Enum.PortDocumentStatusEnum;

import lombok.Data;

@Data
public class PortDocumentResponseDTO {
    private Integer id;
    private Integer tripSegmentId;
    private String name;
    private PortDocumentStatusEnum status;
}
