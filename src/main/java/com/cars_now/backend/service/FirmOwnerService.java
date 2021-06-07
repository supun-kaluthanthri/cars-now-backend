package com.cars_now.backend.service;


import com.cars_now.backend.model.FirmOwner;
import com.cars_now.backend.utils.ResultList;

public interface FirmOwnerService {
    FirmOwner createFirmOwner(FirmOwner firmOwner) throws Exception;

    FirmOwner updateFirmOwner(FirmOwner firmOwner, Long firmOwnerId) throws Exception;

    FirmOwner getFirmOwnerById(Long firmOwnerId) throws Exception;

    ResultList<FirmOwner> getAllFirmOwner(Integer page, Integer size);

    void deleteFirmOwner(Long firmOwnerId) throws Exception;
}
