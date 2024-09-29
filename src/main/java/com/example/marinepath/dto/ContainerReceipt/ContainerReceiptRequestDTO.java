package com.example.marinepath.dto.ContainerReceipt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerReceiptRequestDTO {
    private Integer containerId;
    private Integer portDocumentId;
    private LocalDateTime deliveryDate;
    private String receiverName;
}
