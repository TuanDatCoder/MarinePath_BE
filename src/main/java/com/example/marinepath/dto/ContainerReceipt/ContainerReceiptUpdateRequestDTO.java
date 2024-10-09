package com.example.marinepath.dto.ContainerReceipt;

import com.example.marinepath.entity.Enum.ContainerReceiptStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerReceiptUpdateRequestDTO {
    private Integer containerId;
    private Integer portDocumentId;
    private LocalDateTime deliveryDate;
    private String receiverName;
    private ContainerReceiptStatusEnum status;
}