package com.cars_now.backend.service.impl;

import com.cars_now.backend.dto.CarOwner;
import com.cars_now.backend.exception.NotAcceptableException;
import com.cars_now.backend.exception.NotFoundException;
import com.cars_now.backend.model.Car;
import com.cars_now.backend.repository.CarOwnerRepository;
import com.cars_now.backend.repository.CarRepository;
import com.cars_now.backend.service.CarService;
import com.cars_now.backend.utils.*;
import com.cars_now.backend.utils.modelConverters.CarDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarOwnerServiceImpl.class);

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarOwnerRepository carOwnerRepository;

    @Autowired
    CarDtoConverter carDtoConverter;

    @Autowired
    DtoToResponseConverter dtoToResponseConverter;

    @Override
    public Car createCar(final Car car) throws Exception {

        final CarOwner carOwner = carOwnerRepository.findByCarOwnerId(car.getCarOwnerId());

        if(carOwner == null){
            throw new NotFoundException(ValidationConst.CAR_OWNER_NOT_FOUND, ValidationConst.CAR_OWNER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + car.getCarOwnerId());
        }

        requestValidator.validateCarCreateRequest(car);
        final com.cars_now.backend.dto.Car createdCar = carRepository.save(carDtoConverter.carCreateRequestToCarDto(car, carOwner));

        return dtoToResponseConverter.carDtoToCarResponse(createdCar);
    }

    @Override
    public Car updateCar(final Car car, final Long carId) throws Exception {
        com.cars_now.backend.dto.Car repoCar = carRepository.findByCarId(carId);

        if(repoCar == null) {
            throw new NotFoundException(ValidationConst.CAR_NOT_FOUND, ValidationConst.CAR_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + carId);
        }

        //check car owner is existed
        final CarOwner carOwner = carOwnerRepository.findByCarOwnerId(car.getCarOwnerId());

        if(carOwner == null){
            throw new NotFoundException(ValidationConst.CAR_OWNER_NOT_FOUND, ValidationConst.CAR_OWNER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + car.getCarOwnerId());
        }

        requestValidator.validateCarUpdateRequest(car);
        final com.cars_now.backend.dto.Car updatedCar =  carRepository.
                save(carDtoConverter.carUpdateRequestToCarDto(car, carOwner, repoCar));

        return  dtoToResponseConverter.carDtoToCarResponse(updatedCar);
    }

    @Override
    public com.cars_now.backend.model.Car getCarById(final Long carId) throws Exception{
        final com.cars_now.backend.dto.Car car = carRepository.findByCarId(carId);

        if(car == null) {
            throw new NotFoundException(ValidationConst.CAR_NOT_FOUND, ValidationConst.CAR_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + carId);
        }

        return dtoToResponseConverter.carDtoToCarResponse(car);
    }

    @Override
    public ResultList<Car> getAllCars(Integer page, Integer size){

        final ResultList<Car> carResultList = new ResultList<>();
        final List<Car> carList = new ArrayList<>();
        final Page<com.cars_now.backend.dto.Car> carListItr =  carRepository.
                findAll(PageRequest.of(page,size));

        for (com.cars_now.backend.dto.Car car : carListItr){
            carList.add(dtoToResponseConverter.carDtoToCarResponse(car));
        }

        carResultList.setList(carList);
        carResultList.setTotalCount(carList.size());

        return carResultList;
    }

    @Override
    public void deleteCar(final Long carId) throws Exception{
        final com.cars_now.backend.dto.Car repoCar =  carRepository.findByCarId(carId);

        if(repoCar == null) {
            throw new NotFoundException(ValidationConst.CAR_NOT_FOUND, ValidationConst.CAR_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + carId);
        }

        carRepository.delete(repoCar);

    }

    @Override
    public Car updateCarStatus(final Long carId, final int status) throws Exception {
        /**
         *          1 ----------> available
         *          2 ----------> unavailable
         */
        final com.cars_now.backend.dto.Car repoCar = carRepository.findByCarId(carId);
        if(repoCar == null) {
            throw new NotFoundException(ValidationConst.CAR_NOT_FOUND, ValidationConst.CAR_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + carId);
        }
        //status validation
        if(status != Constant.CAR_AVAILABLE || status != Constant.CAR_UNAVAILABLE) {
            throw new NotAcceptableException(ValidationConst.STATUS_NOT_ACCEPTABLE,status + ValidationConst.STATUS_NOT_ACCEPTABLE.message());
        }
        repoCar.setStatus(status);
        final com.cars_now.backend.dto.Car updatedCar = carRepository.save(repoCar);
        return dtoToResponseConverter.carDtoToCarResponse(updatedCar);
    }

    @Override
    public ResultList<Car> getAllAvailableCars(Integer page, Integer size){
        final ResultList<Car> carResultList = new ResultList<>();
        final List<Car> carList = new ArrayList<>();
        Page<com.cars_now.backend.dto.Car> carsListItr =  carRepository.findByStatus(1, PageRequest.of(page, size));

        for(com.cars_now.backend.dto.Car car : carsListItr) {
            carList.add(dtoToResponseConverter.carDtoToCarResponse(car));
        }

        carResultList.setList(carList);
        carResultList.setTotalCount(carList.size());
        return carResultList;
    }

}
