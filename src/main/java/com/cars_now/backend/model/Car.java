package com.cars_now.backend.model;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Car {
    private Long carId;
    private String brand;
    private String model;
    private String modelYear;
    private String fuelType;
    private String carPlateNumber;
    private int passengerLimit;
    private int allowedKmPerDay;
    private double dailyRate;
    private double additionalRatePerKm;
    private String imageUrl;
    private int status;
    private Long carOwnerId;
}
