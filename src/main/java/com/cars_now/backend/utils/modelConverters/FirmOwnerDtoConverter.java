package com.cars_now.backend.utils.modelConverters;

import com.cars_now.backend.model.FirmOwner;
import com.cars_now.backend.model.Renter;
import org.springframework.stereotype.Component;

@Component
public class FirmOwnerDtoConverter {

    public com.cars_now.backend.dto.FirmOwner firmOwnerCreateRequestToFirmOwnerDto(final FirmOwner firmOwner) throws Exception{
        final com.cars_now.backend.dto.FirmOwner repoFirmOwner = new com.cars_now.backend.dto.FirmOwner();

        repoFirmOwner.setEmail(firmOwner.getEmail());
        repoFirmOwner.setFirstName(firmOwner.getFirstName());
        repoFirmOwner.setLastName(firmOwner.getLastName());
        repoFirmOwner.setFirmAddress(firmOwner.getFirmAddress());

        return repoFirmOwner;

    }

    public com.cars_now.backend.dto.FirmOwner firmOwnerUpdateRequestToFirmOwnerDto(FirmOwner firmOwner, com.cars_now.backend.dto.FirmOwner repoFirmOwner) throws Exception{
        repoFirmOwner.setEmail(firmOwner.getEmail());
        repoFirmOwner.setFirstName(firmOwner.getFirstName());
        repoFirmOwner.setLastName(firmOwner.getLastName());
        repoFirmOwner.setFirmAddress(firmOwner.getFirmAddress());

        return repoFirmOwner;
    }
}
