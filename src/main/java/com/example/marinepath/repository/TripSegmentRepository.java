package com.example.marinepath.repository;


import com.example.marinepath.entity.Enum.TripSegmentStatusEnum;

import com.example.marinepath.entity.TripSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripSegmentRepository extends JpaRepository<TripSegment, Integer> {
    List<TripSegment> findByStatusNot(TripSegmentStatusEnum status);
}
