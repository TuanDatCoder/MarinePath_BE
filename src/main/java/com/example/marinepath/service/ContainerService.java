package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.Container.ContainerRequestDTO;
import com.example.marinepath.dto.Container.ContainerResponseDTO;
import com.example.marinepath.entity.*;
import com.example.marinepath.entity.Enum.Container.ContainerStatusEnum;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContainerService {
    @Autowired
    private ContainerRepository containerRepository;
    @Autowired
    private PortRepository portRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ContainerReceiptRepository containerReceiptRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<ContainerResponseDTO> createContainer(ContainerRequestDTO containerRequestDTO) {
        try {
            Port port = portRepository.findById(containerRequestDTO.getPortId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));

            Order order = orderRepository.findById(containerRequestDTO.getOrderId())
                    .orElseThrow(() -> new ApiException(ErrorCode.ORDER_NOT_FOUND));

            ContainerReceipt containerReceipt = containerReceiptRepository.findById(containerRequestDTO.getContainerReceiptId())
                    .orElseThrow(() -> new ApiException(ErrorCode.CONTAINER_RECEIPT_NOT_FOUND));

            Container container = new Container();
            container.setPort(port);
            container.setOrder(order);
            container.setContainerReceipt(containerReceipt);
            container.setWeight(containerRequestDTO.getWeight());
            container.setContainerCode(containerRequestDTO.getContainerCode());
            container.setContainerOperator(containerRequestDTO.getContainerOperator());
            container.setCargoType(containerRequestDTO.getCargoType());
            container.setStatus(containerRequestDTO.getStatus());

            Container savedContainer = containerRepository.save(container);
            ContainerResponseDTO responseDTO = convertToDto(savedContainer);
            return new ApiResponse<>(201, "Container created successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating container: " + e.getMessage(), null);
        }
    }

    private ContainerResponseDTO convertToDto(Container container) {
        ContainerResponseDTO responseDTO = new ContainerResponseDTO();
        responseDTO = objectMapper.convertValue(container, ContainerResponseDTO.class);

        if (container.getPort() != null) {
            responseDTO.setPortId(container.getPort().getId());
        }
        if (container.getOrder() != null) {
            responseDTO.setOrderId(container.getOrder().getId());
        }
        if (container.getContainerReceipt() != null) {
            responseDTO.setContainerReceiptId(container.getContainerReceipt().getId());
        }
        return responseDTO;
    }

    public ApiResponse<ContainerResponseDTO> updateContainer(Integer id, ContainerRequestDTO containerRequestDTO) {
        try {
            Container existingContainer = containerRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CONTAINER_NOT_FOUND));
            if (existingContainer.getStatus() == ContainerStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.CONTAINER_DELETED);
            }
            Port port = portRepository.findById(containerRequestDTO.getPortId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_NOT_FOUND));
            existingContainer.setPort(port);

            Order order = orderRepository.findById(containerRequestDTO.getOrderId())
                    .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));
            existingContainer.setOrder(order);

            ContainerReceipt containerReceipt = containerReceiptRepository.findById(containerRequestDTO.getContainerReceiptId())
                    .orElseThrow(() -> new ApiException(ErrorCode.COMPANY_NOT_FOUND));
            existingContainer.setContainerReceipt(containerReceipt);

            existingContainer.setWeight(containerRequestDTO.getWeight());
            existingContainer.setContainerOperator(containerRequestDTO.getContainerOperator());
            existingContainer.setContainerCode(containerRequestDTO.getContainerCode());
            existingContainer.setContainerOperator(containerRequestDTO.getContainerOperator());
            existingContainer.setStatus(containerRequestDTO.getStatus());

            Container updatedContainer = containerRepository.save(existingContainer);
            ContainerResponseDTO responseDTO = convertToDto(updatedContainer);
            return new ApiResponse<>(200, "Container updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating container: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deleteContainer(Integer id) {
        try {
            Container container  = containerRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CONTAINER_NOT_FOUND));
            containerRepository.deleteById(id);
            return new ApiResponse<>(204, "Container deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting container: " + e.getMessage(), null);
        }
    }

    public ApiResponse<ContainerResponseDTO> getContainerById(Integer id) {
        try {
            Container container  = containerRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CONTAINER_NOT_FOUND));
            if (container.getStatus() == ContainerStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.CONTAINER_DELETED);
            }
            ContainerResponseDTO responseDTO = convertToDto(container);
            return new ApiResponse<>(200, "Container found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving container: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<ContainerResponseDTO>> getAllContainers() {
        try {
            List<Container> containers = containerRepository.findByStatusNot(ContainerStatusEnum.DELETED);
            List<ContainerResponseDTO> responseDTOs = containers.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "Containers retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving containers: " + e.getMessage(), null);
        }
    }
}
