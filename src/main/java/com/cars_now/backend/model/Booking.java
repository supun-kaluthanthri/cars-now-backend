package com.cars_now.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Booking {
    private Long bookingId;
    private Date bookingStartDate;
    private Date bookingEndDate;
    private Date carReturnDate;
    private int totalDistance;
    private int status;
    private double amount;
    private Long carId;
    private Long carRenterId;
}
