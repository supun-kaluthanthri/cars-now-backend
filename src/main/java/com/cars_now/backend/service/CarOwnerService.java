package com.cars_now.backend.service;

import com.cars_now.backend.model.CarOwner;
import com.cars_now.backend.utils.ResultList;

public interface CarOwnerService {
    CarOwner createCarOwner(CarOwner carOwner) throws Exception;

    CarOwner updateCarOwner(CarOwner carOwner, Long carOwnerId) throws Exception;

    CarOwner getCarOwnerById(Long carOwnerId) throws Exception;

    ResultList<CarOwner> getAllCarOwners(Integer page, Integer size);

    void deleteCarOwner(Long carOwnerId) throws Exception;

    Long getCarOwnerIdByUserId(Integer userId) throws Exception;
}
