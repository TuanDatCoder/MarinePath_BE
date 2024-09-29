package com.example.marinepath.dto.ContainerReceipt;

import com.example.marinepath.entity.Enum.DeliveryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerReceiptResponseDTO {
    private Integer id;
    private Integer containerId;
    private Integer portDocumentId;
    private LocalDateTime deliveryDate;
    private String receiverName;
    private DeliveryStatusEnum status;
}
