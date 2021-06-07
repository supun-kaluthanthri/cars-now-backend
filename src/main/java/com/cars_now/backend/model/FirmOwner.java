package com.cars_now.backend.model;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class FirmOwner {
    private Long firmOwnerId;
    private String email;
    private String firstName;
    private String lastName;
    private String firmAddress;
}
