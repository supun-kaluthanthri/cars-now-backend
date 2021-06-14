package com.cars_now.backend.utils;

public enum ValidationConst {

    ATTRIBUTE_ID(" for id : ", "ATT1000"),
    EMAIL_ID(" for email : ", "ATT1001"),
    USERNAME_ID(" for username : ", "ATT1002"),
    USER_ID(" for user id : ", "ATT1003"),
    RATING(" for rating : ", "ATT1004"),


    RENTER_NOT_FOUND("Renter is not found", "ERR1000"),
    INVALID_EMAIL("Email is not valid", "ERR1001"),
    FIRM_OWNER_NOT_FOUND("Firm owner not found", "ERR1002"),
    CAR_OWNER_NOT_FOUND("Car owner not found", "ERR1003"),
    CAR_NOT_FOUND("Car not found", "ERR1004"),
    STATUS_NOT_ACCEPTABLE(" is not a valid status", "ERR1006"),
    INVALID_GENDER("Gender is not valid", "ERR1007"),
    EMAIL_ALREADY_EXISTED("Email is already existed", "ERR1008"),
    BOOKING_NOT_FOUND("Booking not found", "ERR1009"),
    BOOKING_START_DATE_NOT_ACCEPTABLE("Booking start date cannot be null & must be withing next 7 days", "ERR1010"),
    BOOKING_END_DATE_NOT_ACCEPTABLE("Booking end date comes before start date", "ERR1011"),
    BOOKING_RETURN_DATE_NOT_ACCEPTABLE("Booking return date comes before start date", "ERR1012"),
    FEEDBACK_NOT_FOUND("Feedback is not found", "ERR1013"),
    USER_NOT_FOUND("User is not found ", "ERR1014"),
    USER_EMAIL_ALREADY_EXISTED("User email is already existed ", "ERR1015"),
    USERNAME_ALREADY_EXISTED("User name is already existed ", "ERR1016"),
    ROLE_NOT_FOUND("Role is not found ", "ERR1017"),
    RATING_NOT_ACCEPTABLE("Rating value must be in between 0 and 10 ", "ERR1018"),
    FIRM_OWNER_ALREADY_EXISTED("Firm owner is already existed with this user id ", "ERR1019"),
    CAR_OWNER_ALREADY_EXISTED("Car owner is already existed with this user id ", "ERR1020"),
    RENTER_ALREADY_EXISTED("Renter is already existed with this user id ", "ERR1021"),
    USER_ID_NOT_FOUND("user id is not found ", "ERR1021");

    private String msg;
    private String code;

    ValidationConst(final String msg, final String code) {
        this.msg = msg;
        this.code =  code;
    }

    public String message(){
        return msg;
    }

    public String code(){
        return code;
    }

}
