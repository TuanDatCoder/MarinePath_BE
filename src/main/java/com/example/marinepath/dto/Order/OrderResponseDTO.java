package com.example.marinepath.dto.Order;

import com.example.marinepath.entity.Enum.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Integer id;
    private Integer tripId;
    private Integer customerId;
    private String departure;
    private String arrival;
    private LocalDateTime requestDate;
    private String paymentDetail;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
    private OrderStatusEnum status;
}
