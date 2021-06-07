package com.cars_now.backend.repository;

import com.cars_now.backend.dto.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Long> {

    Renter findByRenterId(Long renterId);
}
