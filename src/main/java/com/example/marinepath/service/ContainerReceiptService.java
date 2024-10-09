package com.example.marinepath.service;

import com.example.marinepath.dto.ApiResponse;
import com.example.marinepath.dto.ContainerReceipt.ContainerReceiptRequestDTO;
import com.example.marinepath.dto.ContainerReceipt.ContainerReceiptResponseDTO;
import com.example.marinepath.dto.ContainerReceipt.ContainerReceiptUpdateRequestDTO;
import com.example.marinepath.entity.Container;
import com.example.marinepath.entity.ContainerReceipt;
import com.example.marinepath.entity.Enum.ContainerReceiptStatusEnum;

import com.example.marinepath.entity.PortDocument;
import com.example.marinepath.exception.ApiException;
import com.example.marinepath.exception.ErrorCode;
import com.example.marinepath.repository.ContainerReceiptRepository;
import com.example.marinepath.repository.ContainerRepository;
import com.example.marinepath.repository.PortDocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContainerReceiptService {

    @Autowired
    private ContainerReceiptRepository containerReceiptRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private PortDocumentRepository portDocumentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiResponse<ContainerReceiptResponseDTO> createContainerReceipt(ContainerReceiptRequestDTO requestDTO) {
        try {
            Container container = containerRepository.findById(requestDTO.getContainerId())
                    .orElseThrow(() -> new ApiException(ErrorCode.CONTAINER_NOT_FOUND));

            PortDocument portDocument = portDocumentRepository.findById(requestDTO.getPortDocumentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_DOCUMENT_NOT_FOUND));

            ContainerReceipt containerReceipt = new ContainerReceipt();
            containerReceipt.setContainer(container);
            containerReceipt.setPortDocument(portDocument);
            containerReceipt.setDeliveryDate(requestDTO.getDeliveryDate());
            containerReceipt.setReceiverName(requestDTO.getReceiverName());
            containerReceipt.setStatus(ContainerReceiptStatusEnum.PENDING);

            ContainerReceipt savedContainerReceipt = containerReceiptRepository.save(containerReceipt);
            ContainerReceiptResponseDTO responseDTO = convertToDto(savedContainerReceipt);
            return new ApiResponse<>(201, "ContainerReceipt created successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating container receipt: " + e.getMessage(), null);
        }
    }

    public ApiResponse<ContainerReceiptResponseDTO> updateContainerReceipt(Integer id, ContainerReceiptUpdateRequestDTO requestDTO) {
        try {
            ContainerReceipt containerReceipt = containerReceiptRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CONTAINER_RECEIPT_NOT_FOUND));

            if (containerReceipt.getStatus() == ContainerReceiptStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.CONTAINER_RECEIPT_DELETED);
            }

            Container container = containerRepository.findById(requestDTO.getContainerId())
                    .orElseThrow(() -> new ApiException(ErrorCode.CONTAINER_NOT_FOUND));

            PortDocument portDocument = portDocumentRepository.findById(requestDTO.getPortDocumentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PORT_DOCUMENT_NOT_FOUND));

            containerReceipt.setContainer(container);
            containerReceipt.setPortDocument(portDocument);
            containerReceipt.setDeliveryDate(requestDTO.getDeliveryDate());
            containerReceipt.setReceiverName(requestDTO.getReceiverName());
            containerReceipt.setStatus(requestDTO.getStatus());

            ContainerReceipt updatedContainerReceipt = containerReceiptRepository.save(containerReceipt);
            ContainerReceiptResponseDTO responseDTO = convertToDto(updatedContainerReceipt);
            return new ApiResponse<>(200, "ContainerReceipt updated successfully", responseDTO);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating container receipt: " + e.getMessage(), null);
        }
    }

    public ApiResponse<Void> deleteContainerReceipt(Integer id) {
        try {
            ContainerReceipt containerReceipt = containerReceiptRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CONTAINER_RECEIPT_NOT_FOUND));

            containerReceiptRepository.delete(containerReceipt);
            return new ApiResponse<>(204, "ContainerReceipt deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting container receipt: " + e.getMessage(), null);
        }
    }

    public ApiResponse<ContainerReceiptResponseDTO> getContainerReceiptById(Integer id) {
        try {
            ContainerReceipt containerReceipt = containerReceiptRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CONTAINER_RECEIPT_NOT_FOUND));

            if (containerReceipt.getStatus() == ContainerReceiptStatusEnum.DELETED) {
                throw new ApiException(ErrorCode.CONTAINER_RECEIPT_DELETED);
            }

            ContainerReceiptResponseDTO responseDTO = convertToDto(containerReceipt);
            return new ApiResponse<>(200, "ContainerReceipt found", responseDTO);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving container receipt: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<ContainerReceiptResponseDTO>> getAllContainerReceipts() {
        try {
            List<ContainerReceipt> containerReceipts = containerReceiptRepository.findByStatusNot(ContainerReceiptStatusEnum.DELETED);
            List<ContainerReceiptResponseDTO> responseDTOs = containerReceipts.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ApiResponse<>(200, "ContainerReceipts retrieved successfully", responseDTOs);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving container receipts: " + e.getMessage(), null);
        }
    }

    private ContainerReceiptResponseDTO convertToDto(ContainerReceipt containerReceipt) {
        ContainerReceiptResponseDTO responseDTO =  objectMapper.convertValue(containerReceipt, ContainerReceiptResponseDTO.class);
        if(containerReceipt.getContainer()!=null){
            responseDTO.setContainerId(containerReceipt.getContainer().getId());
        }
        if(containerReceipt.getPortDocument()!=null){
            responseDTO.setPortDocumentId(containerReceipt.getPortDocument().getId());
        }

        return responseDTO;
    }


}
