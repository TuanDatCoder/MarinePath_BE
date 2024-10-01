package com.example.marinepath.repository;

import com.example.marinepath.entity.Enum.PortStatusEnum;
import com.example.marinepath.entity.Port;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortRepository extends JpaRepository<Port, Integer> {
    List<Port> findByStatusNot(PortStatusEnum portStatusEnum);
}
