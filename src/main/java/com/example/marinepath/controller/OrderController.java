package com.example.marinepath.controller;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Order.OrderRequestDTO;
import com.example.marinepath.dto.Order.OrderResponseDTO;
import com.example.marinepath.dto.Order.OrderUpdateRequestDTO;
import com.example.marinepath.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        ApiResponse<OrderResponseDTO> response = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrder(@PathVariable Integer id, @RequestBody OrderUpdateRequestDTO orderUpdateRequestDTO) {
        ApiResponse<OrderResponseDTO> response = orderService.updateOrder(id, orderUpdateRequestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Integer id) {
        ApiResponse<Void> response = orderService.deleteOrder(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrderById(@PathVariable Integer id) {
        ApiResponse<OrderResponseDTO> response = orderService.getOrderById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrders() {
        ApiResponse<List<OrderResponseDTO>> response = orderService.getAllOrders();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
