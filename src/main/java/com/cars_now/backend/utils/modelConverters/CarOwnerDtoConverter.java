package com.cars_now.backend.utils.modelConverters;

import com.cars_now.backend.model.CarOwner;
import org.springframework.stereotype.Component;

@Component
public class CarOwnerDtoConverter {

    public com.cars_now.backend.dto.CarOwner carOwnerCreateRequestToCarOwnerDto(final CarOwner carOwner) throws Exception{
        final com.cars_now.backend.dto.CarOwner repoCarOwner = new com.cars_now.backend.dto.CarOwner();

        repoCarOwner.setEmail(carOwner.getEmail());
        repoCarOwner.setFirstName(carOwner.getFirstName());
        repoCarOwner.setLastName(carOwner.getLastName());
        repoCarOwner.setGender(carOwner.getGender());
        repoCarOwner.setAddress(carOwner.getAddress());
        repoCarOwner.setMobileNo(carOwner.getMobileNo());
        repoCarOwner.setNicNo(carOwner.getNicNo());

        return repoCarOwner;

    }

    public com.cars_now.backend.dto.CarOwner carOwnerUpdateRequestToCarOwnerDto(CarOwner carOwner, com.cars_now.backend.dto.CarOwner repoCarOwner) throws Exception{
        repoCarOwner.setEmail(carOwner.getEmail());
        repoCarOwner.setFirstName(carOwner.getFirstName());
        repoCarOwner.setLastName(carOwner.getLastName());
        repoCarOwner.setGender(carOwner.getGender());
        repoCarOwner.setAddress(carOwner.getAddress());
        repoCarOwner.setMobileNo(carOwner.getMobileNo());
        repoCarOwner.setNicNo(carOwner.getNicNo());

        return repoCarOwner;
    }
}
