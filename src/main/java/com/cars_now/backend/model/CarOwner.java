package com.cars_now.backend.model;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CarOwner {
    private Long carOwnerId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String address;
    private String nicNo;
    private String mobileNo;
}
