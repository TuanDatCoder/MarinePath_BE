package com.example.marinepath.repository;

import com.example.marinepath.entity.Enum.OrderStatusEnum;
import com.example.marinepath.entity.Enum.TripStatusEnum;
import com.example.marinepath.entity.Order;
import com.example.marinepath.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStatusNot(OrderStatusEnum status);

}
