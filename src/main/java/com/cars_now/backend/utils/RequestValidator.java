package com.cars_now.backend.utils;

import com.cars_now.backend.dto.Users;
import com.cars_now.backend.exception.*;
import com.cars_now.backend.model.*;
import com.cars_now.backend.repository.CarOwnerRepository;
import com.cars_now.backend.repository.UsersDetailRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;


@Component
public class RequestValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestValidator.class);

    @Autowired
    CarOwnerRepository carOwnerRepository;

    @Autowired
    UsersDetailRepository usersDetailRepository;


    public boolean isValidEmailAddress(final String email) {
        if (!isNullOrEmpty(email)) {
            return EmailValidator.getInstance().isValid(email);
        }
        return false;
    }

    private boolean isCarOwnerEmailAlreadyExisted(String email){
        com.cars_now.backend.dto.CarOwner carOwner = carOwnerRepository.findByEmail(email);

        return carOwner!=null;
    }

    private boolean isNullOrEmpty(final String checkString) {
        return checkString == null || StringUtils.containsWhitespace(checkString) || StringUtils.isEmpty(checkString);
    }

    private boolean isStatusValid(final int status){
        /**
         *          1 ----------> available
         *          2 ----------> unavailable
         */

        if(status == 1 || status == 2) return true;
        else  return false;
    }

    private boolean isGenderValid(String gender){
        String[] validGenders =  {"male", "female", "Other"};
        List gendersList = Arrays.asList(validGenders);

        if(gender == null || gender.isBlank() || gendersList.contains(gender)) return true;
        else return false;
    }

    public void validateRenterCreateRequest(Renter renter) throws Exception{
        if(!isValidEmailAddress(renter.getEmail())) {
            throw new RenterValidationException(ValidationConst.INVALID_EMAIL, ValidationConst.INVALID_EMAIL.message());
        }
        if(!isGenderValid(renter.getGender())){
            throw new RenterValidationException(ValidationConst.INVALID_GENDER, ValidationConst.INVALID_GENDER.message());
        }
    }

    public void validateRenterUpdateRequest(Renter renter) throws Exception {
        if(!isValidEmailAddress(renter.getEmail())) {
            throw new RenterValidationException(ValidationConst.INVALID_EMAIL, ValidationConst.INVALID_EMAIL.message());
        }
        if(!isGenderValid(renter.getGender())){
            throw new RenterValidationException(ValidationConst.INVALID_GENDER, ValidationConst.INVALID_GENDER.message());
        }
    }

    public void validateFirmOwnerCreateRequest(FirmOwner firmOwner) throws Exception {
        if(!isValidEmailAddress(firmOwner.getEmail())) {
            throw new FirmOwnerValidationException(ValidationConst.INVALID_EMAIL, ValidationConst.INVALID_EMAIL.message());
        }
    }

    public void validateFirmOwnerUpdateRequest(FirmOwner firmOwner) throws Exception{
        if(!isValidEmailAddress(firmOwner.getEmail())) {
            throw new FirmOwnerValidationException(ValidationConst.INVALID_EMAIL, ValidationConst.INVALID_EMAIL.message());
        }
    }

    public void validateCarOwnerCreateRequest(CarOwner carOwner) throws Exception{
        if(!isValidEmailAddress(carOwner.getEmail())) {
            throw new CarOwnerValidationException(ValidationConst.INVALID_EMAIL, ValidationConst.INVALID_EMAIL.message());
        }
        if(isCarOwnerEmailAlreadyExisted(carOwner.getEmail())) {
            throw new CarOwnerValidationException(ValidationConst.EMAIL_ALREADY_EXISTED, ValidationConst.EMAIL_ALREADY_EXISTED.message());
        }
        if(!isGenderValid(carOwner.getGender())){
            throw new CarOwnerValidationException(ValidationConst.INVALID_GENDER, ValidationConst.INVALID_GENDER.message());
        }
    }

    public void validateCarOwnerUpdateRequest(CarOwner carOwner) throws Exception{
        if(!isValidEmailAddress(carOwner.getEmail())) {
            throw new CarOwnerValidationException(ValidationConst.INVALID_EMAIL, ValidationConst.INVALID_EMAIL.message());
        }
        if(!isGenderValid(carOwner.getGender())){
            throw new CarOwnerValidationException(ValidationConst.INVALID_GENDER, ValidationConst.INVALID_GENDER.message());
        }
    }

    public void validateCarCreateRequest(Car car) throws Exception{
        if(!isStatusValid(car.getStatus())) {
            throw new NotAcceptableException(ValidationConst.STATUS_NOT_ACCEPTABLE,car.getStatus() + ValidationConst.STATUS_NOT_ACCEPTABLE.message());
        }

    }

    public void validateCarUpdateRequest(Car car) throws Exception{
        if(!isStatusValid(car.getStatus())) {
            throw new NotAcceptableException(ValidationConst.STATUS_NOT_ACCEPTABLE,car.getStatus() + ValidationConst.STATUS_NOT_ACCEPTABLE.message());
        }
    }

    public void validateBookingCreateRequest(Booking booking, com.cars_now.backend.dto.Renter renter, com.cars_now.backend.dto.Car car) throws Exception {
        //check whether the renter is existed or not
        if (renter==null) {
            LOGGER.info("renter is not found");
            throw new NotFoundException(ValidationConst.RENTER_NOT_FOUND,
                    ValidationConst.RENTER_NOT_FOUND.message() + ValidationConst.ATTRIBUTE_ID + booking.getCarRenterId());
        }

        // check whether car is existed and available at the booking time.
        if (car==null) {
            LOGGER.info("car is not found");
            throw new NotFoundException(ValidationConst.CAR_NOT_FOUND,
                    ValidationConst.CAR_NOT_FOUND.message() + ValidationConst.ATTRIBUTE_ID.message() + booking.getCarRenterId());
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,7);
        Date checkDate = cal.getTime();


        //check whether the start date is null or not withing the next 7 days
        if (booking.getBookingStartDate() == null || booking.getBookingStartDate().after(checkDate) ){
            throw new NotAcceptableException(ValidationConst.BOOKING_START_DATE_NOT_ACCEPTABLE,
                    ValidationConst.BOOKING_START_DATE_NOT_ACCEPTABLE.message());
        }

        //check whether the end date is before start date
        if(booking.getBookingStartDate().after(booking.getBookingEndDate())) {
           throw  new NotAcceptableException(ValidationConst.BOOKING_END_DATE_NOT_ACCEPTABLE,
                   ValidationConst.BOOKING_END_DATE_NOT_ACCEPTABLE.message());
        }
    }

    public void validateBookingUpdateRequest(Booking booking, com.cars_now.backend.dto.Booking repoBooking, Long bookingId
                                             ) throws Exception{
        if (repoBooking == null) {
            throw new NotFoundException(ValidationConst.BOOKING_NOT_FOUND, ValidationConst.BOOKING_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID + bookingId);
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,7);
        Date checkDate = cal.getTime();

        //check whether the return date is after start date
        if(booking.getCarReturnDate() !=null &&
                booking.getBookingStartDate().after(booking.getCarReturnDate())) {

            throw  new NotAcceptableException(ValidationConst.BOOKING_RETURN_DATE_NOT_ACCEPTABLE,
                    ValidationConst.BOOKING_RETURN_DATE_NOT_ACCEPTABLE.message());
        }




    }

    public void validateUserCreateRequest(UserCreate user) throws Exception {

        Optional<Users> repoUserByEmail = usersDetailRepository.findByEmail(user.getEmail());

        if(repoUserByEmail.isPresent()){
            throw new AlreadyExistedException(ValidationConst.USER_EMAIL_ALREADY_EXISTED,ValidationConst.USER_EMAIL_ALREADY_EXISTED.message() +
                    ValidationConst.EMAIL_ID + user.getEmail());
        }

        Optional<Users> repoUserByUsername = usersDetailRepository.findByUsername(user.getEmail());

        if(repoUserByUsername.isPresent()){
            throw new AlreadyExistedException(ValidationConst.USERNAME_ALREADY_EXISTED,ValidationConst.USERNAME_ALREADY_EXISTED.message() +
                    ValidationConst.USERNAME_ID + user.getEmail());
        }

    }


}

