package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Order.OrderRequestDTO;
import com.example.marinepath.dto.Order.OrderResponseDTO;
import com.example.marinepath.dto.Order.OrderUpdateRequestDTO;
import com.example.marinepath.entity.Customer;
import com.example.marinepath.entity.Enum.OrderStatusEnum;
import com.example.marinepath.entity.Enum.TripSegmentStatusEnum;
import com.example.marinepath.entity.Order;
import com.example.marinepath.entity.Trip;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.CustomerRepository;
import com.example.marinepath.repository.OrderRepository;
import com.example.marinepath.repository.TripRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<OrderResponseDTO> createOrder(OrderRequestDTO orderRequestDTO) {
        try {
            Trip trip = tripRepository.findById(orderRequestDTO.getTripId())
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_FOUND));

            Customer customer = customerRepository.findById(orderRequestDTO.getCustomerId())
                    .orElseThrow(() -> new ApiException(ErrorCode.CUSTOMER_NOT_FOUND));

            Order order = new Order();
            order.setTrip(trip);
            order.setCustomer(customer);
            order.setDeparture(orderRequestDTO.getDeparture());
            order.setArrival(orderRequestDTO.getArrival());
            order.setRequestDate(orderRequestDTO.getRequestDate());
            order.setPaymentDetail(orderRequestDTO.getPaymentDetail());
            order.setIssuedAt(orderRequestDTO.getIssuedAt());
            order.setExpiredAt(orderRequestDTO.getExpiredAt());
            order.setStatus(OrderStatusEnum.SUCCESS);

            Order savedOrder = orderRepository.save(order);
            OrderResponseDTO responseDTO = convertToDto(savedOrder);
            return new ApiResponse<>(201, "Order created successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating order: " + e.getMessage(), null);
        }
    }

    public ApiResponse<OrderResponseDTO> updateOrder(Integer id, OrderUpdateRequestDTO orderUpdateRequestDTO) {
        try {
            Order existingOrder = orderRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.ORDER_NOT_FOUND));

            if (existingOrder.getStatus() == OrderStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.ORDER_DELETED);
            }

            Trip trip = tripRepository.findById(orderUpdateRequestDTO.getTripId())
                    .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_FOUND));

            Customer customer = customerRepository.findById(orderUpdateRequestDTO.getCustomerId())
                    .orElseThrow(() -> new ApiException(ErrorCode.CUSTOMER_NOT_FOUND));

            existingOrder.setTrip(trip);
            existingOrder.setCustomer(customer);
            existingOrder.setDeparture(orderUpdateRequestDTO.getDeparture());
            existingOrder.setArrival(orderUpdateRequestDTO.getArrival());
            existingOrder.setRequestDate(orderUpdateRequestDTO.getRequestDate());
            existingOrder.setPaymentDetail(orderUpdateRequestDTO.getPaymentDetail());
            existingOrder.setIssuedAt(orderUpdateRequestDTO.getIssuedAt());
            existingOrder.setExpiredAt(orderUpdateRequestDTO.getExpiredAt());
            existingOrder.setStatus(orderUpdateRequestDTO.getStatus());

            Order updatedOrder = orderRepository.save(existingOrder);
            OrderResponseDTO responseDTO = convertToDto(updatedOrder);
            return new ApiResponse<>(200, "Order updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating order: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deleteOrder(Integer id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.ORDER_NOT_FOUND));

            orderRepository.deleteById(id);
            return new ApiResponse<>(204, "Order deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting order: " + e.getMessage(), null);
        }
    }

    public ApiResponse<OrderResponseDTO> getOrderById(Integer id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.ORDER_NOT_FOUND));

            if (order.getStatus() == OrderStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.ORDER_DELETED);
            }

            OrderResponseDTO responseDTO = convertToDto(order);
            return new ApiResponse<>(200, "Order found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving order: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<OrderResponseDTO>> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findByStatusNot(OrderStatusEnum.DELETED);
            List<OrderResponseDTO> responseDTOs = orders.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Orders retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving orders: " + e.getMessage(), null);
        }
    }

    private OrderResponseDTO convertToDto(Order order) {
        OrderResponseDTO responseDTO = objectMapper.convertValue(order, OrderResponseDTO.class);

        if (order.getTrip() != null) {
            responseDTO.setTripId(order.getTrip().getId());
        }
        if (order.getCustomer() != null) {
            responseDTO.setCustomerId(order.getCustomer().getId());
        }
        return responseDTO;
    }
}
