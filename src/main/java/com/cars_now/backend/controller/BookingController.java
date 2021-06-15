package com.cars_now.backend.controller;

import com.cars_now.backend.model.Booking;
import com.cars_now.backend.model.Car;
import com.cars_now.backend.service.BookingService;
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

@CrossOrigin
@RestController
@RequestMapping("/bookings")
@Api(description = "crud operations for the booking", tags = "booking")
public class BookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private static final String CREATE_BOOKING = "hasAuthority('PERMISSION_CREATE_BOOKING')";
    private static final String UPDATE_BOOKING = "hasAuthority('PERMISSION_UPDATE_BOOKING')";
    private static final String DELETE_BOOKING = "hasAuthority('PERMISSION_DELETE_BOOKING')";
    private static final String READ_BOOKING = "hasAuthority('PERMISSION_READ_BOOKING')";

    @Autowired
    BookingService bookingService;

    @ApiOperation(value = "Creates a booking", notes = "Enter necessary details to the given attributes to create a new booking")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully created a new booking", response = Booking.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid | Empty Input Parameters in the Request"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to create the new booking"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Creating the particular booking is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while creating the new booking")
    })
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize(CREATE_BOOKING)
    public ResponseEntity<Object> createBooking(final @ApiParam(value = "Enter necessary details to create a new booking", required = true) @RequestBody Booking booking) throws Exception {
        LOGGER.trace("Create booking api invoked");
        final Booking createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }


//    @ApiOperation(value = "Updates a booking", notes = "Enter the booking id of a particular booking you want to update")
//    @ApiResponses(value = {
//            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully updated the booking", response = Booking.class),
//            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to update the booking"),
//            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Updating the particular booking is forbidden"),
//            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The booking you were trying to update is not found"),
//            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while updating the booking")
//    })
//    @RequestMapping(value = "/{bookingId}", method = RequestMethod.PUT)
//    public ResponseEntity<Object> updateBooking(@Valid final @RequestBody Booking booking, final @ApiParam(value = "booking id to update", required = true) @PathVariable("bookingId") Long bookingId) throws Exception {
//        LOGGER.info("Update booking api invoked. ");
//
//        final Booking updateBooking = bookingService.updateBooking(booking, bookingId);
//        return new ResponseEntity<>(updateBooking, HttpStatus.OK);
//    }


    @ApiOperation(value = "Get a booking", notes = "Enter the booking id of a particular booking you want to retrieve")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the booking", response = Booking.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the booking"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the booking you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The booking you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the booking")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{bookingId}")
    @PreAuthorize(READ_BOOKING)
    public ResponseEntity<Object> getBookingId( @Valid final @ApiParam(value = "booking id to retrieve", required = true) @PathVariable("bookingId") Long bookingId) throws Exception {
        LOGGER.info("Get booking API invoked");

        final Booking bookingResponse = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the bookings", notes = "Will retrieve a list of all the  bookings")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the bookings list", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the bookings list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the bookings list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The bookings list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the bookings list")
    })
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize(READ_BOOKING)
    public ResponseEntity<Object> getAllBookings(final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                             final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all bookings API invoked");

        final ResultList<Booking> bookingList = bookingService.getAllBookings(page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(bookingList.getTotalCount()));
        return new ResponseEntity<>(bookingList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a booking", notes = "Enter the booking id of a particular booking you want to delete")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully deleted the booking", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to delete the booking"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Deleting the particular booking is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The booking you were trying to delete is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while deleting the booking")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{bookingId}")
    @PreAuthorize(DELETE_BOOKING)
    public ResponseEntity<Object> deleteBooking(final @ApiParam(value = "booking id you want to delete", required = true) @PathVariable("bookingId") Long bookingId) throws Exception {
        LOGGER.info("Delete booking API invoked");

        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiOperation(value = "Updates a booking status", notes = "Enter the booking id of a particular booking you want to update the status")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully updated the booking status", response = Booking.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to update the booking status"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Updating the particular booking status is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The booking you were trying to update the status is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while updating the booking status")
    })
    @RequestMapping(value = "/update-booking-status/{bookingId}", method = RequestMethod.POST)
    public ResponseEntity<Object> updateBookingStatus(final @ApiParam(value = "booking id to update the status", required = true) @PathVariable("bookingId") Long bookingId,
                                                  final @RequestParam(name = "status",required = true) String status) throws Exception {
        LOGGER.info("Update booking status api invoked. ");

        final Booking updatedBooking = bookingService.updateBookingStatus(bookingId, Integer.parseInt(status));
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the bookings by renter", notes = " retrieve a list of all the bookings by renter")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the renter's booking list", response = Booking.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the renter's booking list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the renter's booking list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The renter's booking list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the renter's booking list")
    })
    @PreAuthorize(READ_BOOKING)
    @RequestMapping(value = "/bookings-by-renter/{renterId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getBookingsByRenter(final @ApiParam(value = "renter id to get bookings", required = true) @PathVariable("renterId") Long renterId,
                                                      final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get renter's bookings API invoked");

        final ResultList<Booking> bookingList = bookingService.getAllBookingsByRenter(renterId,page,size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(bookingList.getTotalCount()));
        return new ResponseEntity<>(bookingList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the bookings by car", notes = " retrieve a list of all the bookings by car")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the car's booking list", response = Booking.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the car's booking list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the car's booking list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The car's booking list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the car's booking list")
    })
    @RequestMapping(value = "/bookings-by-car/{carId}", method = RequestMethod.GET)
    @PreAuthorize(READ_BOOKING)
    public ResponseEntity<Object> getBookingsByCar(final @ApiParam(value = "car id to get bookings", required = true) @PathVariable("carId") Long carId,
                                                      final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get car's bookings API invoked");

        final ResultList<Booking> bookingList = bookingService.getAllBookingsByCar(carId,page,size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(bookingList.getTotalCount()));
        return new ResponseEntity<>(bookingList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the uncompleted bookings", notes = "Will retrieve a list of all the  uncompleted bookings")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the uncompleted bookings list", response = Booking.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the uncompleted bookings list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the uncompleted bookings list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The uncompleted bookings list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the uncompleted bookings list")
    })
    @RequestMapping(value = "/uncompleted-bookings",method = RequestMethod.GET)
    @PreAuthorize(READ_BOOKING)
    public ResponseEntity<Object> getAllUncompletedBookings(final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                 final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all uncompleted bookings API invoked");

        final ResultList<Booking> bookingList = bookingService.getAllUncompletedBookings(page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(bookingList.getTotalCount()));
        return new ResponseEntity<>(bookingList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Get the total amount", notes = "Enter the booking id of a particular booking you want to retrieve total amount")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the booking' amount", response = Booking.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the booking's amount"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the booking's amount you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The booking's amount you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the booking's amount")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/get-amount/{bookingId}")
    @PreAuthorize(READ_BOOKING)
    public ResponseEntity<Object> getAmount(@Valid final @ApiParam(value = "booking id to retrieve", required = true) @PathVariable("bookingId") Long bookingId,
                                            final @RequestParam(name = "return date", required = true) String returnDate,
                                            final @RequestParam(name = "total distance", required = true) int totalDistance) throws Exception {
        LOGGER.info("Get booking API invoked");

        final Double totalAmount = bookingService.calculateAmount(bookingId, returnDate, totalDistance);
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }


    @ApiOperation(value = "Updates booking amount", notes = "Enter the booking id of a particular booking you want to update amount")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully updated the booking amount", response = Booking.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to update the booking amount"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Updating the particular booking amount is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The booking amount you were trying to update is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while updating the booking amount")
    })
    @RequestMapping(value = "/make-payment/{bookingId}", method = RequestMethod.PUT)
    @PreAuthorize(UPDATE_BOOKING)
    public ResponseEntity<Object> makePayment(final @ApiParam(value = "booking id to update the amount", required = true) @PathVariable("bookingId") Long bookingId,
                                              final @RequestParam(name = "return date", required = true) String returnDate,
                                              final @RequestParam(name = "amount",required = true) Double amount,
                                              final @RequestParam(name = "total distance", required = true) int totalDistance) throws Exception {
        LOGGER.info("Update booking amount api invoked. ");

        final Booking updatedBooking = bookingService.makePayment(bookingId, amount, returnDate,totalDistance);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

}
