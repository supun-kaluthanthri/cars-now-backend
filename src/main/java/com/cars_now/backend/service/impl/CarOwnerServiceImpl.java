package com.cars_now.backend.service.impl;

import com.cars_now.backend.exception.NotFoundException;
import com.cars_now.backend.model.CarOwner;
import com.cars_now.backend.repository.CarOwnerRepository;
import com.cars_now.backend.service.CarOwnerService;
import com.cars_now.backend.utils.DtoToResponseConverter;
import com.cars_now.backend.utils.RequestValidator;
import com.cars_now.backend.utils.ResultList;
import com.cars_now.backend.utils.ValidationConst;
import com.cars_now.backend.utils.modelConverters.CarOwnerDtoConverter;
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
public class CarOwnerServiceImpl implements CarOwnerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarOwnerServiceImpl.class);

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    CarOwnerRepository carOwnerRepository;

    @Autowired
    CarOwnerDtoConverter carOwnerDtoConverter;

    @Autowired
    DtoToResponseConverter dtoToResponseConverter;

    @Override
    public CarOwner createCarOwner(final CarOwner carOwner) throws Exception {
        requestValidator.validateCarOwnerCreateRequest(carOwner);
        final com.cars_now.backend.dto.CarOwner createdCarOwner = carOwnerRepository.save(carOwnerDtoConverter.carOwnerCreateRequestToCarOwnerDto(carOwner));

        return dtoToResponseConverter.carOwnerDtoToCarOwnerResponse(createdCarOwner);
    }

    @Override
    public CarOwner updateCarOwner(final CarOwner carOwner, final Long carOwnerId) throws Exception {
        com.cars_now.backend.dto.CarOwner repoCarOwner = carOwnerRepository.findByCarOwnerId(carOwnerId);

        if(repoCarOwner == null) {
            throw new NotFoundException(ValidationConst.CAR_OWNER_NOT_FOUND, ValidationConst.CAR_OWNER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + carOwnerId);
        }

        requestValidator.validateCarOwnerUpdateRequest(carOwner);
        final com.cars_now.backend.dto.CarOwner updatedCarOwner =  carOwnerRepository.
                save(carOwnerDtoConverter.carOwnerUpdateRequestToCarOwnerDto(carOwner, repoCarOwner));

        return  dtoToResponseConverter.carOwnerDtoToCarOwnerResponse(updatedCarOwner);
    }

    @Override
    public CarOwner getCarOwnerById(final Long carOwnerId) throws Exception{
        final com.cars_now.backend.dto.CarOwner carOwner = carOwnerRepository.findByCarOwnerId(carOwnerId);

        if(carOwner == null) {
            throw new NotFoundException(ValidationConst.CAR_OWNER_NOT_FOUND, ValidationConst.CAR_OWNER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + carOwnerId);
        }

        return dtoToResponseConverter.carOwnerDtoToCarOwnerResponse(carOwner);
    }

    @Override
    public ResultList<CarOwner> getAllCarOwners(Integer page, Integer size){

        final ResultList<CarOwner> carOwnerResultList = new ResultList<>();
        final List<CarOwner> carOwnerList = new ArrayList<>();
        final Page<com.cars_now.backend.dto.CarOwner> carOwnerListItr =  carOwnerRepository.
                findAll(PageRequest.of(page,size));

        for (com.cars_now.backend.dto.CarOwner carOwner : carOwnerListItr){
            carOwnerList.add(dtoToResponseConverter.carOwnerDtoToCarOwnerResponse(carOwner));
        }

        carOwnerResultList.setList(carOwnerList);
        carOwnerResultList.setTotalCount(carOwnerList.size());

        return carOwnerResultList;
    }

    @Override
    public void deleteCarOwner(final Long carOwnerId) throws Exception {
        final com.cars_now.backend.dto.CarOwner carOwner = carOwnerRepository.findByCarOwnerId(carOwnerId);

        if (carOwner != null) {
            carOwnerRepository.delete(carOwner);
        } else {
            throw new NotFoundException(ValidationConst.CAR_OWNER_NOT_FOUND, ValidationConst.CAR_OWNER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + carOwnerId);
        }
    }

    @Override
    public Long getCarOwnerIdByUserId(Integer userId) throws Exception {
        final Optional<com.cars_now.backend.dto.CarOwner> carOwner = carOwnerRepository.findByUserId(userId);

        if (carOwner.isPresent()) {
            return carOwner.get().getCarOwnerId();
        } else {
            throw new NotFoundException(ValidationConst.CAR_OWNER_NOT_FOUND, ValidationConst.CAR_OWNER_NOT_FOUND.message() +
                    ValidationConst.USER_ID.message() + userId);
        }
    }
}
