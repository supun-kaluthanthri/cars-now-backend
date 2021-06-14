package com.cars_now.backend.repository;

import com.cars_now.backend.dto.CarOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {

    CarOwner findByCarOwnerId(Long carOwnerId);
    CarOwner findByEmail(String email);

    Optional<CarOwner> findByUserId(Integer userId);
}
