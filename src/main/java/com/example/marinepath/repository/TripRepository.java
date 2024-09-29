package com.example.marinepath.repository;

import com.example.marinepath.entity.Enum.TripStatusEnum;
import com.example.marinepath.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    List<Trip> findByStatusNot(TripStatusEnum status);

}
