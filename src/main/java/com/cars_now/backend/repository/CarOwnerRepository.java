package com.cars_now.backend.repository;

import com.cars_now.backend.dto.CarOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {

    CarOwner findByCarOwnerId(Long carOwnerId);
    CarOwner findByEmail(String email);
}
