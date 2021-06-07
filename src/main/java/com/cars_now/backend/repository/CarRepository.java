package com.cars_now.backend.repository;

import com.cars_now.backend.dto.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByCarId(Long carId);
    Car findByCarIdAndStatus(Long carId, int status);
    Page<Car> findByStatus(int status, Pageable pageable);
}
