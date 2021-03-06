package com.cars_now.backend.utils;

import com.cars_now.backend.model.*;
import org.springframework.stereotype.Component;

@Component
public class DtoToResponseConverter {

    /**
     * Convert Renter dto to renter model object.
     */
    public Renter renterDtoToRenterResponse(final com.cars_now.backend.dto.Renter repoRenter){
        final Renter renter = new Renter();
        renter.setRenterId(repoRenter.getRenterId());
        renter.setFirstName(repoRenter.getFirstName());
        renter.setLastName(repoRenter.getLastName());
        renter.setAddress(repoRenter.getAddress());
        renter.setGender(repoRenter.getGender());
        renter.setEmail(repoRenter.getEmail());
        renter.setLicenseNo(repoRenter.getLicenseNo());
        renter.setMobileNo(repoRenter.getMobileNo());
        renter.setNicNo(repoRenter.getNicNo());

        return renter;
    }

    /**
     * Convert FirmOwner dto to FirmOwner model object.
     */
    public FirmOwner firmOwnerDtoToFirmOwnerResponse(final com.cars_now.backend.dto.FirmOwner repoFirmOwner){
        final FirmOwner firmOwner = new FirmOwner();

        firmOwner.setFirmOwnerId(repoFirmOwner.getFirmOwnerId());
        firmOwner.setEmail(repoFirmOwner.getEmail());
        firmOwner.setFirstName(repoFirmOwner.getFirstName());
        firmOwner.setLastName(repoFirmOwner.getLastName());
        firmOwner.setFirmAddress(repoFirmOwner.getFirmAddress());


        return firmOwner;
    }

    /**
     * Convert CarOwner dto to CarOwner model object.
     */
    public CarOwner carOwnerDtoToCarOwnerResponse(final com.cars_now.backend.dto.CarOwner repoCarOwner){
        final CarOwner carOwner = new CarOwner();

        carOwner.setCarOwnerId(repoCarOwner.getCarOwnerId());
        carOwner.setEmail(repoCarOwner.getEmail());
        carOwner.setFirstName(repoCarOwner.getFirstName());
        carOwner.setLastName(repoCarOwner.getLastName());
        carOwner.setAddress(repoCarOwner.getAddress());
        carOwner.setGender(repoCarOwner.getGender());
        carOwner.setMobileNo(repoCarOwner.getMobileNo());
        carOwner.setNicNo(repoCarOwner.getNicNo());

        return carOwner;
    }

    /**
     * Convert Car dto to Car model object.
     */
    public Car carDtoToCarResponse(com.cars_now.backend.dto.Car createdCar) {
        final Car car = new Car();

        car.setCarId(createdCar.getCarId());
        car.setCarOwnerId(createdCar.getCarOwner().getCarOwnerId());
        car.setBrand(createdCar.getBrand());
        car.setModel(createdCar.getModel());
        car.setModelYear(createdCar.getModelYear());
        car.setFuelType(createdCar.getFuelType());
        car.setCarPlateNumber(createdCar.getCarPlateNumber());
        car.setAllowedKmPerDay(createdCar.getAllowedKmPerDay());
        car.setDailyRate(createdCar.getDailyRate());
        car.setAdditionalRatePerKm(createdCar.getAdditionalRatePerKm());
        car.setPassengerLimit(createdCar.getPassengerLimit());
        car.setStatus(createdCar.getStatus());

        return car;
    }

    /**
     * Convert Booking dto to Booking model object.
     */
    public Booking bookingDtoToBookingResponse(com.cars_now.backend.dto.Booking createdBooking) {
        final Booking booking = new Booking();

        booking.setBookingId(createdBooking.getBookingId());
        booking.setBookingStartDate(createdBooking.getBookingStartDate());
        booking.setBookingEndDate(createdBooking.getBookingEndDate());
        booking.setCarReturnDate(createdBooking.getCarReturnDate());
        booking.setTotalDistance(createdBooking.getTotalDistance());
        booking.setStatus(createdBooking.getStatus());
        booking.setAmount(createdBooking.getAmount());
        booking.setCarRenterId(createdBooking.getRenter().getRenterId());
        booking.setCarId(createdBooking.getCar().getCarId());

        return booking;
    }

    /**
     * Convert Feedback dto to Feedback model object.
     */
    public Feedback feedBackDtoToFeedbackResponse(com.cars_now.backend.dto.Feedback createdFeedback){
        final Feedback feedBack = new Feedback();

        feedBack.setFeedbackId(createdFeedback.getFeedbackId());
        feedBack.setMessage(createdFeedback.getMessage());
        feedBack.setRenterId(createdFeedback.getRenter().getRenterId());

        return feedBack;
    }
}
