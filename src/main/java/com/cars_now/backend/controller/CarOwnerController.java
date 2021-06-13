package com.cars_now.backend.controller;

import com.cars_now.backend.model.CarOwner;
import com.cars_now.backend.service.CarOwnerService;
import com.cars_now.backend.utils.ControllerAttributes;
import com.cars_now.backend.utils.ResultList;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.HttpURLConnection;

@RestController
@CrossOrigin
@RequestMapping("/car-owners")
@Api(description = "crud operations for the carOwner", tags = "car-owner")
public class CarOwnerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarOwnerController.class);

    private static final String CREATE_CAR_OWNER = "hasAuthority('PERMISSION_CREATE_CAR_OWNER')";
    private static final String UPDATE_CAR_OWNER = "hasAuthority('PERMISSION_UPDATE_CAR_OWNER')";
    private static final String DELETE_CAR_OWNER = "hasAuthority('PERMISSION_DELETE_CAR_OWNER')";
    private static final String READ_CAR_OWNER = "hasAuthority('PERMISSION_READ_CAR_OWNER')";

    @Autowired
    CarOwnerService carOwnerService;

    @ApiOperation(value = "Creates a car owner", notes = "Enter necessary details to the given attributes to create a new car owner")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully created a new car owner", response = CarOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid | Empty Input Parameters in the Request"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to create the new car owner"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Creating the particular car owner is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while creating the new car owner")
    })
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize(CREATE_CAR_OWNER)
    public ResponseEntity<Object> createCarOwner(final @ApiParam(value = "Enter necessary details to create a new car owner", required = true) @RequestBody CarOwner carOwner) throws Exception {
        LOGGER.trace("Create car owner api invoked");
        final CarOwner createdCarOwner = carOwnerService.createCarOwner(carOwner);
        return new ResponseEntity<>(createdCarOwner, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Updates a car owner", notes = "Enter the car owner id of a particular car owner you want to update")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully updated the car owner", response = CarOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to update the car owner"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Updating the particular car owner is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The car owner you were trying to update is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while updating the car owner")
    })
    @RequestMapping(value = "/{carOwnerId}", method = RequestMethod.PUT)
    @PreAuthorize(UPDATE_CAR_OWNER)
    public ResponseEntity<Object> updateCarOwner(@Valid final @RequestBody CarOwner carOwner, final @ApiParam(value = "carOwner id to update", required = true) @PathVariable("carOwnerId") Long carOwnerId) throws Exception {
        LOGGER.info("Update carOwner api invoked. ");

        final CarOwner updateCarOwner = carOwnerService.updateCarOwner(carOwner,carOwnerId);
        return new ResponseEntity<>(updateCarOwner, HttpStatus.OK);
    }


    @ApiOperation(value = "Gets a car owner", notes = "Enter the car owner id of a particular car owner you want to retrieve")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the car owner", response = CarOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the car owner"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the car owner you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The car owner you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the car owner")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{carOwnerId}")
    @PreAuthorize(READ_CAR_OWNER)
    public ResponseEntity<Object> getCarOwner( @Valid final @ApiParam(value = "carOwner id to retrieve", required = true) @PathVariable("carOwnerId") Long carOwnerId) throws Exception {
        LOGGER.info("Get carOwner API invoked");

        final CarOwner carOwnerResponse = carOwnerService.getCarOwnerById(carOwnerId);
        return new ResponseEntity<>(carOwnerResponse, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the carOwners", notes = "Will retrieve a list of all the available carOwners")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the carOwners list", response = CarOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the carOwners list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the carOwners list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The carOwners list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the carOwners list")
    })
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize(READ_CAR_OWNER)
    public ResponseEntity<Object> getAllCarOwners(final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                   final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all carOwners API invoked");

        final ResultList<CarOwner> carOwnerList = carOwnerService.getAllCarOwners(page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(carOwnerList.getTotalCount()));
        return new ResponseEntity<>(carOwnerList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a carOwner", notes = "Enter the carOwner id of a particular carOwner you want to delete")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully deleted the carOwner", response = CarOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to delete the carOwner"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Deleting the particular carOwner is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The carOwner you were trying to delete is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while deleting the carOwner")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{carOwnerId}")
    @PreAuthorize(DELETE_CAR_OWNER)
    public ResponseEntity<Object> deleteCarOwners(final @ApiParam(value = "carOwner id you want to delete", required = true) @PathVariable("carOwnerId") Long carOwnerId) throws Exception {
        LOGGER.info("Delete carOwner API invoked");

        carOwnerService.deleteCarOwner(carOwnerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
