package com.cars_now.backend.repository;

import com.cars_now.backend.dto.FirmOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmOwnerRepository extends JpaRepository<FirmOwner, Long> {

    FirmOwner findByFirmOwnerId(Long firmOwnerId);
}
