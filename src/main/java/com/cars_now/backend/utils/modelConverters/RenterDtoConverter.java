package com.cars_now.backend.utils.modelConverters;

import com.cars_now.backend.model.Renter;
import org.springframework.stereotype.Component;

@Component
public class RenterDtoConverter {

    public com.cars_now.backend.dto.Renter renterCreateRequestToRenterDto(final Renter renter) throws Exception{
        final com.cars_now.backend.dto.Renter returnRenter = new com.cars_now.backend.dto.Renter();

        returnRenter.setFirstName(renter.getFirstName());
        returnRenter.setLastName(renter.getLastName());
        returnRenter.setAddress(renter.getAddress());
        returnRenter.setEmail(renter.getEmail());
        returnRenter.setGender(renter.getGender());
        returnRenter.setLicenseNo(renter.getLicenseNo());
        returnRenter.setMobileNo(renter.getMobileNo());
        returnRenter.setNicNo(renter.getNicNo());
        returnRenter.setUserId(renter.getUserId());
        return returnRenter;
    }

    public com.cars_now.backend.dto.Renter renterUpdateRequestToRenterDto(Renter renter, com.cars_now.backend.dto.Renter repoRenter) throws Exception{
        repoRenter.setFirstName(renter.getFirstName());
        repoRenter.setLastName(renter.getLastName());
        repoRenter.setEmail(renter.getEmail());
        repoRenter.setGender(renter.getGender());
        repoRenter.setNicNo(renter.getNicNo());
        repoRenter.setMobileNo(renter.getMobileNo());
        repoRenter.setLicenseNo(renter.getLicenseNo());

        return repoRenter;
    }
}
