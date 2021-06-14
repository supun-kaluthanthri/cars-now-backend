package com.cars_now.backend.service.impl;

import com.cars_now.backend.exception.NotFoundException;
import com.cars_now.backend.model.FirmOwner;
import com.cars_now.backend.repository.FirmOwnerRepository;
import com.cars_now.backend.service.FirmOwnerService;
import com.cars_now.backend.utils.DtoToResponseConverter;
import com.cars_now.backend.utils.RequestValidator;
import com.cars_now.backend.utils.ResultList;
import com.cars_now.backend.utils.ValidationConst;
import com.cars_now.backend.utils.modelConverters.FirmOwnerDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FirmOwnerServiceImpl implements FirmOwnerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirmOwnerServiceImpl.class);

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    FirmOwnerRepository firmOwnerRepository;

    @Autowired
    FirmOwnerDtoConverter firmOwnerDtoConverter;

    @Autowired
    DtoToResponseConverter dtoToResponseConverter;

    @Override
    public FirmOwner createFirmOwner(final FirmOwner firmOwner) throws Exception {
        requestValidator.validateFirmOwnerCreateRequest(firmOwner);
        final com.cars_now.backend.dto.FirmOwner createdFirmOwner = firmOwnerRepository.save(firmOwnerDtoConverter.firmOwnerCreateRequestToFirmOwnerDto(firmOwner));

        return dtoToResponseConverter.firmOwnerDtoToFirmOwnerResponse(createdFirmOwner);
    }

    @Override
    public FirmOwner updateFirmOwner(final FirmOwner firmOwner, final Long firmOwnerId) throws Exception {
        com.cars_now.backend.dto.FirmOwner repoFirmOwner = firmOwnerRepository.findByFirmOwnerId(firmOwnerId);

        if(repoFirmOwner == null) {
            throw new NotFoundException(ValidationConst.FIRM_OWNER_NOT_FOUND, ValidationConst.FIRM_OWNER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + firmOwnerId);
        }

        requestValidator.validateFirmOwnerUpdateRequest(firmOwner);
        final com.cars_now.backend.dto.FirmOwner updatedFirmOwner =  firmOwnerRepository.
                save(firmOwnerDtoConverter.firmOwnerUpdateRequestToFirmOwnerDto(firmOwner, repoFirmOwner));

        return  dtoToResponseConverter.firmOwnerDtoToFirmOwnerResponse(updatedFirmOwner);
    }

    @Override
    public FirmOwner getFirmOwnerById(final Long firmOwnerId) throws Exception{
        final com.cars_now.backend.dto.FirmOwner firmOwner = firmOwnerRepository.findByFirmOwnerId(firmOwnerId);

        if(firmOwner == null) {
            throw new NotFoundException(ValidationConst.FIRM_OWNER_NOT_FOUND, ValidationConst.FIRM_OWNER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + firmOwnerId);
        }

        return dtoToResponseConverter.firmOwnerDtoToFirmOwnerResponse(firmOwner);
    }

    @Override
    public ResultList<FirmOwner> getAllFirmOwner(Integer page, Integer size){

        final ResultList<FirmOwner> firmOwnerResultList = new ResultList<>();
        final List<FirmOwner> firmOwnerList = new ArrayList<>();
        final Page<com.cars_now.backend.dto.FirmOwner> firmOwnerListItr =  firmOwnerRepository.
                findAll(PageRequest.of(page,size));

        for (com.cars_now.backend.dto.FirmOwner firmOwner : firmOwnerListItr){
            firmOwnerList.add(dtoToResponseConverter.firmOwnerDtoToFirmOwnerResponse(firmOwner));
        }

        firmOwnerResultList.setList(firmOwnerList);
        firmOwnerResultList.setTotalCount(firmOwnerList.size());

        return firmOwnerResultList;
    }

    @Override
    public void deleteFirmOwner(final Long firmOwnerId) throws Exception {
        final com.cars_now.backend.dto.FirmOwner firmOwner = firmOwnerRepository.findByFirmOwnerId(firmOwnerId);
        if (firmOwner != null) {
            firmOwnerRepository.delete(firmOwner);
        } else {
            throw new NotFoundException(ValidationConst.FIRM_OWNER_NOT_FOUND, ValidationConst.FIRM_OWNER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + firmOwnerId);
        }
    }

    @Override
    public Long getFirmOwnerId(Integer userId) throws Exception{
        final Optional<com.cars_now.backend.dto.FirmOwner> firmOwner = firmOwnerRepository.findByUserId(userId);

        if(!firmOwner.isPresent()) {
            throw new NotFoundException(ValidationConst.FIRM_OWNER_NOT_FOUND, ValidationConst.FIRM_OWNER_NOT_FOUND.message() +
                    ValidationConst.USER_ID.message() + userId);
        }

        return firmOwner.get().getFirmOwnerId();
    }
}
