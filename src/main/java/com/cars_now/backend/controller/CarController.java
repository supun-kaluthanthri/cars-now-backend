package com.cars_now.backend.controller;

import com.cars_now.backend.model.Car;
import com.cars_now.backend.service.CarService;
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
@RequestMapping("/cars")
@Api(description = "crud operations for the car", tags = "car")
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private static final String CREATE_CAR = "hasAuthority('PERMISSION_CREATE_CAR')";
    private static final String UPDATE_CAR = "hasAuthority('PERMISSION_UPDATE_CAR')";
    private static final String DELETE_CAR = "hasAuthority('PERMISSION_DELETE_CAR')";
    private static final String READ_CAR = "hasAuthority('PERMISSION_READ_CAR')";

    @Autowired
    CarService carService;

    @ApiOperation(value = "Creates a car", notes = "Enter necessary details to the given attributes to create a new car")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully created a new car", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid | Empty Input Parameters in the Request"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to create the new car"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Creating the particular car is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while creating the new car")
    })
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize(CREATE_CAR)
    public ResponseEntity<Object> createCar(final @ApiParam(value = "Enter necessary details to create a new car", required = true) @RequestBody Car car) throws Exception {
        LOGGER.trace("Create Car api invoked");
        final Car createdCar = carService.createCar(car);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Updates a car", notes = "Enter the car id of a particular car you want to update")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully updated the car", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to update the car"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Updating the particular car is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The car you were trying to update is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while updating the car")
    })
    @RequestMapping(value = "/{carId}", method = RequestMethod.PUT)
    @PreAuthorize(UPDATE_CAR)
    public ResponseEntity<Object> updateCar(@Valid final @RequestBody Car car, final @ApiParam(value = "car id to update", required = true) @PathVariable("carId") Long carId) throws Exception {
        LOGGER.info("Update car api invoked. ");

        final Car updateCar = carService.updateCar(car,carId);
        return new ResponseEntity<>(updateCar, HttpStatus.OK);
    }


    @ApiOperation(value = "Gets a car", notes = "Enter the car id of a particular car you want to retrieve")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the car", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the car"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the car you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The car you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the car")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{carId}")
    @PreAuthorize(READ_CAR)
    public ResponseEntity<Object> getCarOwner( @Valid final @ApiParam(value = "car id to retrieve", required = true) @PathVariable("carId") Long carId) throws Exception {
        LOGGER.info("Get car API invoked");

        final Car carResponse = carService.getCarById(carId);
        return new ResponseEntity<>(carResponse, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the cars", notes = "Will retrieve a list of all the available cars")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the cars list", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the cars list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the cars list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The cars list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the cars list")
    })
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize(READ_CAR)
    public ResponseEntity<Object> getAllCars(final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                             final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all cars API invoked");

        final ResultList<Car> carList = carService.getAllCars(page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(carList.getTotalCount()));
        return new ResponseEntity<>(carList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a car", notes = "Enter the car id of a particular car you want to delete")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully deleted the car", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to delete the car"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Deleting the particular car is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The car you were trying to delete is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while deleting the car")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{carId}")
    @PreAuthorize(DELETE_CAR)
    public ResponseEntity<Object> deleteCar(final @ApiParam(value = "car id you want to delete", required = true) @PathVariable("carId") Long carId) throws Exception {
        LOGGER.info("Delete car API invoked");

        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiOperation(value = "Updates a car status", notes = "Enter the car id of a particular car you want to update the status")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully updated the car status", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to update the car status"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Updating the particular car status is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The car you were trying to update the status is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while updating the car status")
    })
    @RequestMapping(value = "/update-car-status/{carId}", method = RequestMethod.PUT)
    @PreAuthorize(UPDATE_CAR)
    public ResponseEntity<Object> updateCarStatus(final @ApiParam(value = "car id to update the status", required = true) @PathVariable("carId") Long carId,
                                                  final @RequestParam(name = "status",required = true) String status) throws Exception {
        LOGGER.info("Update car status api invoked. ");

        final Car updateCar = carService.updateCarStatus(carId, Integer.parseInt(status));
        return new ResponseEntity<>(updateCar, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the available cars", notes = "Will retrieve a list of all the available cars at the moment")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the available cars list", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the available cars list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the available cars list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The available cars list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the available cars list")
    })
    @RequestMapping(value = "/all-available-cars", method = RequestMethod.GET)
    @PreAuthorize(READ_CAR)
    public ResponseEntity<Object> getAllAvailableCars(final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                             final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all the available cars API invoked");

        final ResultList<Car> carList = carService.getAllAvailableCars(page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(carList.getTotalCount()));
        return new ResponseEntity<>(carList, responseHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all the cars by car owner", notes = "Will retrieve a list of all the cars of the car owner")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the owner's cars list", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the available owner's cars list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the available owner's cars list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The owner's cars list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the owner's cars list")
    })
    @RequestMapping(value = "/cars-by-car-owner/{carOwnerId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCarsByCarOwner(final @ApiParam(value = "car owner id", required = true) @PathVariable("carOwnerId") Long carOwnerId,
                                                    final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all the  cars of a particular owner API invoked");
        final ResultList<Car> carList = carService.getCarsByCarOwner(carOwnerId, page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(carList.getTotalCount()));
        return new ResponseEntity<>(carList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the cars by car owner", notes = "Will retrieve a list of all the cars of the car owner")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the owner's cars list", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the available owner's cars list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the available owner's cars list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The owner's cars list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the owner's cars list")
    })
    @RequestMapping(value = "/cars-by-car-owner", method = RequestMethod.GET)
    @PreAuthorize(READ_CAR)
    public ResponseEntity<Object> getCarsByCarOwner(final @ApiParam(value = "car owner id", required = true) @PathVariable("carOwnerId") Long carOwnerId,
                                                    final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all the  cars of a particular owner API invoked");

        final ResultList<Car> carList = carService.getCarsByCarOwner(carOwnerId, page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(carList.getTotalCount()));
        return new ResponseEntity<>(carList.getList(), responseHeaders, HttpStatus.OK);
    }
}
