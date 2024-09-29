package com.example.marinepath.repository;

import com.example.marinepath.entity.Container;
import com.example.marinepath.entity.Enum.Container.ContainerStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContainerRepository  extends JpaRepository<Container, Integer> {
    List<Container> findByStatusNot(ContainerStatusEnum containerStatusEnum);
}
