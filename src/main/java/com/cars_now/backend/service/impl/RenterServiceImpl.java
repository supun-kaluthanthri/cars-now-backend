package com.cars_now.backend.service.impl;

import com.cars_now.backend.exception.RenterNotFoundException;
import com.cars_now.backend.model.Renter;
import com.cars_now.backend.repository.RenterRepository;
import com.cars_now.backend.service.RenterService;
import com.cars_now.backend.utils.DtoToResponseConverter;
import com.cars_now.backend.utils.RequestValidator;
import com.cars_now.backend.utils.ResultList;
import com.cars_now.backend.utils.ValidationConst;
import com.cars_now.backend.utils.modelConverters.RenterDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RenterServiceImpl implements RenterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RenterServiceImpl.class);

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    RenterRepository renterRepository;

    @Autowired
    RenterDtoConverter renterDtoConverter;

    @Autowired
    DtoToResponseConverter dtoToResponseConverter;



    @Override
    public Renter createRenter(final Renter renter) throws Exception {
        requestValidator.validateRenterCreateRequest(renter);
        final com.cars_now.backend.dto.Renter createdRenter = renterRepository.save(renterDtoConverter.renterCreateRequestToRenterDto(renter));

        return dtoToResponseConverter.renterDtoToRenterResponse(createdRenter);
    }

    @Override
    public Renter updateRenter(final Renter renter, final Long renterId) throws Exception {
        com.cars_now.backend.dto.Renter repoRenter = renterRepository.findByRenterId(renterId);
        if(repoRenter == null) {
            throw new RenterNotFoundException(ValidationConst.RENTER_NOT_FOUND, ValidationConst.RENTER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + renterId);
        }

        requestValidator.validateRenterUpdateRequest(renter);
        final com.cars_now.backend.dto.Renter updatedRenter =  renterRepository.
                save(renterDtoConverter.renterUpdateRequestToRenterDto(renter, repoRenter));

        return  dtoToResponseConverter.renterDtoToRenterResponse(updatedRenter);
    }

    @Override
    public Renter getRenterById(final Long renterId) throws Exception{
        final com.cars_now.backend.dto.Renter renter = renterRepository.findByRenterId(renterId);

        if(renter == null) {
            throw new RenterNotFoundException(ValidationConst.RENTER_NOT_FOUND, ValidationConst.RENTER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + renterId);
        }

        return dtoToResponseConverter.renterDtoToRenterResponse(renter);
    }

    @Override
    public ResultList<Renter> getAllRenters(Integer page, Integer size){
        final ResultList<Renter> renterResultList = new ResultList<>();
        final List<Renter> renterList = new ArrayList<>();
        final Page<com.cars_now.backend.dto.Renter> renterListItr =  renterRepository.
                findAll(PageRequest.of(page,size));

        for (com.cars_now.backend.dto.Renter renter : renterListItr){
            renterList.add(dtoToResponseConverter.renterDtoToRenterResponse(renter));
        }

        renterResultList.setList(renterList);
        renterResultList.setTotalCount(renterList.size());

        return renterResultList;
    }

    @Override
    public void deleteRenter(final Long renterId) throws Exception {
        final com.cars_now.backend.dto.Renter renter = renterRepository.findByRenterId(renterId);
        if (renter != null) {
            renterRepository.delete(renter);
        } else {
            throw new RenterNotFoundException(ValidationConst.RENTER_NOT_FOUND, ValidationConst.RENTER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + renterId);
        }
    }
}
