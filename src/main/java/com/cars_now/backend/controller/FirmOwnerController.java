package com.cars_now.backend.controller;

import com.cars_now.backend.model.FirmOwner;
import com.cars_now.backend.service.FirmOwnerService;
import com.cars_now.backend.utils.ControllerAttributes;
import com.cars_now.backend.utils.ResultList;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.HttpURLConnection;

@RestController
@CrossOrigin
@RequestMapping("/firm-owners")
@Api(description = "crud operations for the firmOwner", tags = "firm-owner")
public class FirmOwnerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirmOwnerController.class);

    @Autowired
    private FirmOwnerService firmOwnerService;

    @ApiOperation(value = "Creates a firm owner", notes = "Enter necessary details to the given attributes to create a new firm owner")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully created a new firm owner", response = FirmOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid | Empty Input Parameters in the Request"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to create the new firm owner"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Creating the particular firm owner is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while creating the new firm owner")
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createFirmOwner(final @ApiParam(value = "Enter necessary details to create a new firm owner", required = true) @RequestBody FirmOwner firmOwner) throws Exception {
        LOGGER.trace("Create firm owner api invoked");
        final FirmOwner createdFirmOwner = firmOwnerService.createFirmOwner(firmOwner);
        return new ResponseEntity<>(createdFirmOwner, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Updates a firm owner", notes = "Enter the firm owner id of a particular firm owner you want to update")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully updated the firm owner", response = FirmOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to update the firm owner"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Updating the particular firm owner is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The firm owner you were trying to update is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while updating the firm owner")
    })
    @RequestMapping(value = "/{firmOwnerId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateFirmOwner(@Valid final @RequestBody FirmOwner firmOwner, final @ApiParam(value = "firmOwner id to update", required = true) @PathVariable("firmOwnerId") Long firmOwnerId) throws Exception {
        LOGGER.info("Update firmOwner api invoked. ");

        final FirmOwner updateFirmOwner = firmOwnerService.updateFirmOwner(firmOwner,firmOwnerId);
        return new ResponseEntity<>(updateFirmOwner, HttpStatus.OK);
    }


    @ApiOperation(value = "Gets a firm owner", notes = "Enter the firm owner id of a particular firm owner you want to retrieve")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the firm owner", response = FirmOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the firm owner"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the firm owner you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The firm owner you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the firm owner")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{firmOwnerId}")
    public ResponseEntity<Object> getFirmOwner( @Valid final @ApiParam(value = "firmOwner id to retrieve", required = true) @PathVariable("firmOwnerId") Long firmOwnerId) throws Exception {
        LOGGER.info("Get firmOwner API invoked");

        final FirmOwner firmOwnerResponse = firmOwnerService.getFirmOwnerById(firmOwnerId);
        return new ResponseEntity<>(firmOwnerResponse, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the firmOwner", notes = "Will retrieve a list of all the available firmOwners")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the firmOwner list", response = FirmOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the firmOwner list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the firmOwner list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The firmOwner list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the firmOwner list")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAllFirmOwners(final @RequestParam(name = "orderBy", required = false) String orderBy,
                                                final @RequestParam(name = "direction", required = false) String direction,
                                                final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all firmOwners API invoked");

        final ResultList<FirmOwner> firmOwnerList = firmOwnerService.getAllFirmOwner(page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(firmOwnerList.getTotalCount()));
        return new ResponseEntity<>(firmOwnerList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a firmOwner", notes = "Enter the firmOwner id of a particular firmOwner you want to delete")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully deleted the firmOwner", response = FirmOwner.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to delete the firmOwner"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Deleting the particular firmOwner is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The firmOwner you were trying to delete is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while deleting the firmOwner")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{firmOwnerId}")
    public ResponseEntity<Object> deleteFirmOwners(final @ApiParam(value = "firmOwner id you want to delete", required = true) @PathVariable("firmOwnerId") Long firmOwnerId) throws Exception {
        LOGGER.info("Delete firmOwner API invoked");

        firmOwnerService.deleteFirmOwner(firmOwnerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
