package com.cars_now.backend.service;

import com.cars_now.backend.model.Renter;
import com.cars_now.backend.utils.ResultList;

public interface RenterService {
    Renter createRenter(Renter renter) throws Exception;

    Renter updateRenter(Renter renter, Long renterId) throws Exception;

    Renter getRenterById(Long renterId) throws Exception;


    ResultList<Renter> getAllRenters(Integer page, Integer size);

    void deleteRenter(Long renterID) throws  Exception;

    Long getRenterId(Integer userId) throws Exception;
}
