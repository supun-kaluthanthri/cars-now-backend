package com.cars_now.backend.controller;

import com.cars_now.backend.model.Renter;
import com.cars_now.backend.service.RenterService;
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
@RequestMapping("/renters")
@Api(description = "crud operations for the renter", tags = "renter")
public class RenterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RenterController.class);

    @Autowired
    private RenterService renterService;

    @ApiOperation(value = "Creates a renter", notes = "Enter necessary details to the given attributes to create a new renter")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully created a new renter", response = Renter.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid | Empty Input Parameters in the Request"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to create the new renter"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Creating the particular renter is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while creating the new renter")
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createRenter(final @ApiParam(value = "Enter necessary details to create a new renter", required = true) @RequestBody Renter renter) throws Exception {
        LOGGER.trace("Create renter api invoked");
        final Renter createdRenter = renterService.createRenter(renter);
        return new ResponseEntity<>(createdRenter, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Updates a renter", notes = "Enter the renter id of a particular renter you want to update")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully updated the renter", response = Renter.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to update the renter"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Updating the particular renter is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The renter you were trying to update is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while updating the renter")
    })
    @RequestMapping(value = "/{renterId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateRenter(@Valid final @RequestBody Renter renter, final @ApiParam(value = "Renter id to update", required = true) @PathVariable("renterId") Long renterId) throws Exception {
        LOGGER.info("Update renter api invoked. ");

        final Renter renterResponse = renterService.updateRenter(renter, renterId);
        return new ResponseEntity<>(renterResponse, HttpStatus.OK);
    }


    @ApiOperation(value = "Gets a renter", notes = "Enter the renter id of a particular renter you want to retrieve")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the renter", response = Renter.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the renter"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the renter you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The renter you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the renter")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{renterId}")
    public ResponseEntity<Object> getRenter( @Valid final @ApiParam(value = "Renter id to retrieve", required = true) @PathVariable("renterId") Long renterId) throws Exception {
        LOGGER.info("Get renter API invoked");

        final Renter renterResponse = renterService.getRenterById(renterId);
        return new ResponseEntity<>(renterResponse, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the renters", notes = "Will retrieve a list of all the available renters")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the renter list", response = Renter.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the renter list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the renters list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The renters list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the renters list")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAllRenters(final @RequestParam(name = "orderBy", required = false) String orderBy,
                                                  final @RequestParam(name = "direction", required = false) String direction,
                                                  final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all renters API invoked");

        final ResultList<Renter> renterList = renterService.getAllRenters(page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(renterList.getTotalCount()));
        return new ResponseEntity<>(renterList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a renter", notes = "Enter the renter id of a particular renter you want to delete")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully deleted the renter", response = Renter.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to delete the renter"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Deleting the particular renter is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The renter you were trying to delete is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while deleting the renter")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{renterID}")
    public ResponseEntity<Object> deleteRenter(final @ApiParam(value = "Renter id you want to delete", required = true) @PathVariable("renterID") Long renterID) throws Exception {
        LOGGER.info("Delete renter API invoked");

        renterService.deleteRenter(renterID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
