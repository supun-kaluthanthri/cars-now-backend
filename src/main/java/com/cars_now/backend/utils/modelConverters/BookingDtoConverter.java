package com.cars_now.backend.utils.modelConverters;

import com.cars_now.backend.dto.Car;
import com.cars_now.backend.dto.Renter;
import com.cars_now.backend.model.Booking;
import com.cars_now.backend.utils.Constant;
import org.springframework.stereotype.Component;

@Component
public class BookingDtoConverter {


    public com.cars_now.backend.dto.Booking bookingCreateRequestToBookingDto (final Booking booking,
                                                                              final Renter renter, final Car car) throws Exception{

        final com.cars_now.backend.dto.Booking repoBooking = new com.cars_now.backend.dto.Booking();
        repoBooking.setBookingStartDate(booking.getBookingStartDate());
        repoBooking.setBookingEndDate(booking.getBookingEndDate());
//        repoBooking.setCarReturnDate(booking.getCarReturnDate());
        repoBooking.setTotalDistance(booking.getTotalDistance());
        repoBooking.setStatus(Constant.BOOKING_BOOKED);
        repoBooking.setAmount(booking.getAmount());
        repoBooking.setCar(car);
        repoBooking.setRenter(renter);

        return repoBooking;
    }

    public com.cars_now.backend.dto.Booking bookingUpdateRequestToBookingDto(Booking booking, com.cars_now.backend.dto.Booking repoBooking) {
        //repoBooking.setBookingStartDate(booking.getBookingStartDate());
        repoBooking.setBookingEndDate(booking.getBookingEndDate());
        repoBooking.setCarReturnDate(booking.getCarReturnDate());
        repoBooking.setTotalDistance(booking.getTotalDistance());
        repoBooking.setStatus(booking.getStatus());
        repoBooking.setAmount(booking.getAmount());
        //repoBooking.setRenter(renter);
        //repoBooking.setCar(car);

        return repoBooking;
    }
}
