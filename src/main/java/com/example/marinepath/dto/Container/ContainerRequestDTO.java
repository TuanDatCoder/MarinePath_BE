package com.example.marinepath.dto.Container;

import com.example.marinepath.entity.Enum.Container.CargoTypeEnum;
import com.example.marinepath.entity.Enum.Container.ContainerStatusEnum;
import lombok.Data;

@Data
public class ContainerRequestDTO {
    private Integer startPort;
    private Integer endPort;
    private Integer orderId;
    private Integer containerReceiptId;
    private String containerCode;
    private Float weight;
    private String containerOperator;
    private CargoTypeEnum cargoType;
    private ContainerStatusEnum status;
}
