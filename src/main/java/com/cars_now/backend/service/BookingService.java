package com.cars_now.backend.service;

import com.cars_now.backend.model.Booking;
import com.cars_now.backend.utils.ResultList;

import java.util.Date;

public interface BookingService {
    Booking createBooking(Booking booking) throws Exception;

    Booking updateBooking(Booking booking, Long bookingId) throws Exception;

    ResultList<Booking> getAllBookings(Integer page, Integer size) throws Exception;

    Booking getBookingById(Long bookingId) throws Exception;

    void deleteBooking(Long bookingId) throws Exception;

    Booking updateBookingStatus(Long bookingId, int status) throws Exception;

    ResultList<Booking> getAllBookingsByRenter(Long renterId, Integer page, Integer size) throws Exception;

    ResultList<Booking> getAllBookingsByCar(Long carId, Integer page, Integer size) throws Exception;

    ResultList<Booking> getAllUncompletedBookings(Integer page, Integer size) throws Exception;

    Double calculateAmount(Long booking, Date returnDate, int totalDistance) throws Exception;

    Booking makePayment(Long bookingId, Double amount) throws Exception;
}
