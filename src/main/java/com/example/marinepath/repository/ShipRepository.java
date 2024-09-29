package com.example.marinepath.repository;

import com.example.marinepath.entity.Enum.Ship.ShipStatusEnum;
import com.example.marinepath.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Integer> {

    List<Ship> findByStatusNot(ShipStatusEnum shipStatusEnum);
}
