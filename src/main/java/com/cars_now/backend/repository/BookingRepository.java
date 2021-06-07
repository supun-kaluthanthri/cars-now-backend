package com.cars_now.backend.repository;

import com.cars_now.backend.dto.Booking;
import com.cars_now.backend.dto.Car;
import com.cars_now.backend.dto.Renter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findByBookingId(Long bookingId);

    Page<Booking> findByRenter(Renter renter, Pageable pageable);
    Page<Booking> findByStatusNot(int status, Pageable pageable);
    Page<Booking> findByCar(Car car, Pageable pageable);
}
