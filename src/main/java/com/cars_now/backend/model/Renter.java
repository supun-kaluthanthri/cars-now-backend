package com.cars_now.backend.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Renter {
    private Long renterId;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String email;
    private String nicNo;
    private String mobileNo;
    private String licenseNo;
    private Integer userId;
}
