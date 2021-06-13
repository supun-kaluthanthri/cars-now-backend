package com.cars_now.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserCreate {
    private Integer id;
    private String email;
    private String password;
    private List<Integer> roleIds;
}
