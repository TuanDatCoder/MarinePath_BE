package com.example.marinepath.repository;

import com.example.marinepath.entity.ContainerReceipt;
import com.example.marinepath.entity.Enum.DeliveryStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerReceiptRepository extends JpaRepository<ContainerReceipt, Integer> {
    List<ContainerReceipt> findByStatusNot(DeliveryStatusEnum status);
}