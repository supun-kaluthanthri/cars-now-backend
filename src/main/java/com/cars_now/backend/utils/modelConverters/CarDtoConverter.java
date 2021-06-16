package com.cars_now.backend.utils.modelConverters;

import com.cars_now.backend.dto.CarOwner;
import com.cars_now.backend.model.Car;
import com.cars_now.backend.utils.Constant;
import org.springframework.stereotype.Component;

@Component
public class CarDtoConverter {

    public com.cars_now.backend.dto.Car carCreateRequestToCarDto(final Car car, final CarOwner carOwner) throws Exception{
        final com.cars_now.backend.dto.Car repoCar = new com.cars_now.backend.dto.Car();

        repoCar.setBrand(car.getBrand());
        repoCar.setModel(car.getModel());
        repoCar.setModelYear(car.getModelYear());
        repoCar.setFuelType(car.getFuelType());
        repoCar.setCarPlateNumber(car.getCarPlateNumber());
        repoCar.setAllowedKmPerDay(car.getAllowedKmPerDay());
        repoCar.setDailyRate(car.getDailyRate());
        repoCar.setAdditionalRatePerKm(car.getAdditionalRatePerKm());
        repoCar.setPassengerLimit(car.getPassengerLimit());
        repoCar.setImageUrl(car.getImageUrl());
        repoCar.setStatus(Constant.CAR_UNAVAILABLE);
        repoCar.setCarOwner(carOwner);

        return repoCar;
    }

    public com.cars_now.backend.dto.Car carUpdateRequestToCarDto(Car car, CarOwner carOwner, com.cars_now.backend.dto.Car repoCar) {
        repoCar.setBrand(car.getBrand());
        repoCar.setModel(car.getModel());
        repoCar.setModelYear(car.getModelYear());
        repoCar.setFuelType(car.getFuelType());
        repoCar.setCarPlateNumber(car.getCarPlateNumber());
        repoCar.setAllowedKmPerDay(car.getAllowedKmPerDay());
        repoCar.setDailyRate(car.getDailyRate());
        repoCar.setAdditionalRatePerKm(car.getAdditionalRatePerKm());
        repoCar.setPassengerLimit(car.getPassengerLimit());
        repoCar.setImageUrl(car.getImageUrl());
        repoCar.setStatus(repoCar.getStatus());
        repoCar.setCarOwner(carOwner);

        return repoCar;
    }
}
