package com.cars_now.backend.service.impl;


import com.cars_now.backend.exception.NotAcceptableException;
import com.cars_now.backend.exception.NotFoundException;
import com.cars_now.backend.model.Booking;
import com.cars_now.backend.repository.BookingRepository;
import com.cars_now.backend.repository.CarRepository;
import com.cars_now.backend.repository.RenterRepository;
import com.cars_now.backend.service.BookingService;
import com.cars_now.backend.service.CarService;
import com.cars_now.backend.utils.*;
import com.cars_now.backend.utils.modelConverters.BookingDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarOwnerServiceImpl.class);

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingDtoConverter bookingDtoConverter;

    @Autowired
    RenterRepository renterRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    DtoToResponseConverter dtoToResponseConverter;

    @Autowired
    CarService carService;

    @Override
    public Booking createBooking(final Booking booking) throws Exception {
        LOGGER.info("crreate booking 1");
        final com.cars_now.backend.dto.Renter renter = renterRepository.findByRenterId(booking.getCarRenterId());
        final com.cars_now.backend.dto.Car car = carRepository.findByCarIdAndStatus(booking.getCarId(), Constant.CAR_AVAILABLE);
        LOGGER.info("before validation");
        requestValidator.validateBookingCreateRequest(booking, renter, car);
        LOGGER.info("ater validation");
        //create a booking with updated status
        final com.cars_now.backend.dto.Booking createdBooking = bookingRepository.save(bookingDtoConverter.bookingCreateRequestToBookingDto(booking, renter, car));
        //updating the car status
        LOGGER.info("before updating status");
        carService.updateCarStatus(createdBooking.getCar().getCarId(), Constant.CAR_UNAVAILABLE);
        LOGGER.info("after updating sttus");
        return dtoToResponseConverter.bookingDtoToBookingResponse(createdBooking);
    }


    @Override
    public Booking updateBooking(final Booking booking, final Long bookingId) throws Exception {
        com.cars_now.backend.dto.Booking repoBooking = bookingRepository.findByBookingId(bookingId);
        //final com.cars_now.backend.dto.Renter renter = renterRepository.findByRenterId(booking.getCarRenterId());
        //final com.cars_now.backend.dto.Car car = carRepository.findByCarIdAndStatus(booking.getCarId(), 1);
        requestValidator.validateBookingUpdateRequest(booking, repoBooking, bookingId);

        final com.cars_now.backend.dto.Booking updatedBooking = bookingRepository.save(bookingDtoConverter.bookingUpdateRequestToBookingDto(booking, repoBooking));
        return dtoToResponseConverter.bookingDtoToBookingResponse(updatedBooking);
    }


    @Override
    public com.cars_now.backend.model.Booking updateBookingStatus(final Long bookingId, final int status) throws Exception {
        /**
         *          1 ----------> booked
         *          2 ----------> in progress
         *          3 ----------> completed
         */
        final com.cars_now.backend.dto.Booking repoBooking = bookingRepository.findByBookingId(bookingId);
        if(repoBooking == null) {
            throw new NotFoundException(ValidationConst.BOOKING_NOT_FOUND, ValidationConst.BOOKING_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + bookingId);
        }
        //status validation
        if(status != Constant.BOOKING_BOOKED && status != Constant.BOOKING_IN_PROGRESS
                && status != Constant.BOOKING_COMPLETED) {
            throw new NotAcceptableException(ValidationConst.STATUS_NOT_ACCEPTABLE,status + ValidationConst.STATUS_NOT_ACCEPTABLE.message());
        }
        repoBooking.setStatus(status);
        final com.cars_now.backend.dto.Booking updatedBooking = bookingRepository.save(repoBooking);
        return dtoToResponseConverter.bookingDtoToBookingResponse(updatedBooking);
    }


    @Override
    public void deleteBooking(final Long bookingId) throws Exception {
        final com.cars_now.backend.dto.Booking booking = bookingRepository.findByBookingId(bookingId);

        if (booking == null) {
            throw new NotFoundException(ValidationConst.BOOKING_NOT_FOUND, ValidationConst.BOOKING_NOT_FOUND.message());
        }

        bookingRepository.delete(booking);
    }


    @Override
    public ResultList<Booking> getAllBookings(Integer page, Integer size) throws Exception {
        final ResultList<Booking> bookingResultList = new ResultList<>();
        final List<Booking> bookingList =  new ArrayList<>();
        final Page<com.cars_now.backend.dto.Booking> bookingListItr = bookingRepository.findAll(PageRequest.of(page, size));

        for (com.cars_now.backend.dto.Booking booking : bookingListItr) {
            bookingList.add(dtoToResponseConverter.bookingDtoToBookingResponse(booking));
        }

        bookingResultList.setList(bookingList);
        bookingResultList.setTotalCount(bookingList.size());
        return bookingResultList;
    }

    @Override
    public Booking getBookingById(final Long bookingId) throws Exception{
        final com.cars_now.backend.dto.Booking booking = bookingRepository.findByBookingId(bookingId);

        if (booking == null) {
            throw new NotFoundException(ValidationConst.BOOKING_NOT_FOUND, ValidationConst.BOOKING_NOT_FOUND.message());
        }

        return dtoToResponseConverter.bookingDtoToBookingResponse(booking);
    }

    @Override
    public ResultList<Booking> getAllBookingsByRenter(final Long renterId, Integer page, Integer size) throws Exception {
        final ResultList<Booking> bookingResultList = new ResultList<>();
        final List<Booking> bookingList =  new ArrayList<>();

        final com.cars_now.backend.dto.Renter renter = renterRepository.findByRenterId(renterId);
        if (renter == null){
            throw new NotFoundException(ValidationConst.RENTER_NOT_FOUND,
                    ValidationConst.RENTER_NOT_FOUND.message() + ValidationConst.ATTRIBUTE_ID + renterId);
        }

        final Page<com.cars_now.backend.dto.Booking> bookingListItr = bookingRepository.findByRenter(renter, PageRequest.of(page, size));
        for (com.cars_now.backend.dto.Booking booking : bookingListItr) {
            bookingList.add(dtoToResponseConverter.bookingDtoToBookingResponse(booking));
        }

        bookingResultList.setList(bookingList);
        bookingResultList.setTotalCount(bookingList.size());
        return bookingResultList;
    }

    @Override
    public ResultList<Booking> getAllBookingsByCar(final Long carId, Integer page, Integer size) throws Exception {
        final ResultList<Booking> bookingResultList = new ResultList<>();
        final List<Booking> bookingList =  new ArrayList<>();

        final com.cars_now.backend.dto.Car car = carRepository.findByCarId(carId);
        if (car == null){
            throw new NotFoundException(ValidationConst.CAR_NOT_FOUND,
                    ValidationConst.CAR_NOT_FOUND.message() + ValidationConst.ATTRIBUTE_ID + carId);
        }

        final Page<com.cars_now.backend.dto.Booking> bookingListItr = bookingRepository.findByCar(car, PageRequest.of(page, size));
        for (com.cars_now.backend.dto.Booking booking : bookingListItr) {
            bookingList.add(dtoToResponseConverter.bookingDtoToBookingResponse(booking));
        }

        bookingResultList.setList(bookingList);
        bookingResultList.setTotalCount(bookingList.size());
        return bookingResultList;
    }

    @Override
    public ResultList<Booking> getAllUncompletedBookings(Integer page, Integer size) throws Exception {
        int status = 3;
        final ResultList<Booking> bookingResultList = new ResultList<>();
        final List<Booking> bookingList =  new ArrayList<>();
        final Page<com.cars_now.backend.dto.Booking> bookingListItr = bookingRepository.findByStatusNot(status, PageRequest.of(page, size));

        for (com.cars_now.backend.dto.Booking booking : bookingListItr) {
            bookingList.add(dtoToResponseConverter.bookingDtoToBookingResponse(booking));
        }

        bookingResultList.setList(bookingList);
        bookingResultList.setTotalCount(bookingList.size());
        return bookingResultList;
    }

    @Override
    public Double calculateAmount(final Long bookingId, final String returnDateString, int totalDistance) throws Exception {
        double totalAmount = 0;
        double extraCharges = 0;
        double totalAllowedDistance = 0;
        long differenceInTime;
        long differenceInDays;

        final com.cars_now.backend.dto.Booking repoBooking = bookingRepository.findByBookingId(bookingId);

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date returnDate =  formatter.parse(returnDateString);
        LOGGER.info("after converting to date obj");
        if(repoBooking == null) {
            throw new NotFoundException(ValidationConst.BOOKING_NOT_FOUND, ValidationConst.BOOKING_NOT_FOUND.message());
        }

        //check whether return date is earlier than start date
        if(repoBooking.getBookingStartDate().after(returnDate)) {
            throw new NotFoundException(ValidationConst.BOOKING_START_DATE_NOT_ACCEPTABLE,
                    ValidationConst.BOOKING_START_DATE_NOT_ACCEPTABLE.message());
        }
        LOGGER.info("start calculation");
        differenceInTime = returnDate.getTime() -  repoBooking.getBookingStartDate().getTime();

        differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24)) % 365;
        totalAllowedDistance = differenceInDays * repoBooking.getCar().getAllowedKmPerDay();
        if(totalDistance > totalAllowedDistance ) {
            extraCharges =  repoBooking.getCar().getAdditionalRatePerKm() * (totalDistance - totalAllowedDistance);
        }
        LOGGER.info("calculate amount");
        totalAmount =  repoBooking.getCar().getDailyRate() * differenceInDays + extraCharges;

        return totalAmount;
    }

    @Override
    public Booking makePayment(final Long bookingId, final Double amount, final String returnDateString, int totalDistance) throws Exception{
        final com.cars_now.backend.dto.Booking booking = bookingRepository.findByBookingId(bookingId);

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date returnDate =  formatter.parse(returnDateString);

        if (booking == null) {
            throw new NotFoundException(ValidationConst.BOOKING_NOT_FOUND,
                    ValidationConst.BOOKING_NOT_FOUND.message() + ValidationConst.ATTRIBUTE_ID + bookingId);
        }

        booking.setAmount(amount);
        booking.setCarReturnDate(returnDate);
        booking.setTotalDistance(totalDistance);
        final Booking updatedBooking = dtoToResponseConverter.bookingDtoToBookingResponse(bookingRepository.save(booking));
        final Booking updatedStatusBooking = updateBookingStatus(bookingId, Constant.BOOKING_COMPLETED);
        carService.updateCarStatus(updatedStatusBooking.getCarId(), Constant.CAR_AVAILABLE);
        return updatedStatusBooking;
    }

}
