package com.cars_now.backend.service;

import com.cars_now.backend.model.Car;
import com.cars_now.backend.utils.ResultList;

public interface CarService {
    Car createCar(Car car) throws Exception;

    Car updateCar(Car car, Long carId) throws Exception;

    Car getCarById(Long carId) throws Exception;

    ResultList<Car> getAllCars(Integer page, Integer size);

    void deleteCar(Long carId) throws Exception;

    Car updateCarStatus(Long carId, int status) throws Exception;

    ResultList<Car> getAllAvailableCars(Integer page, Integer size);
}
