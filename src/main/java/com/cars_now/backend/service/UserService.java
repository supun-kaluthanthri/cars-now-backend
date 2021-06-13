package com.cars_now.backend.service;

import com.cars_now.backend.model.User;
import com.cars_now.backend.model.UserCreate;

public interface UserService {
    User createUser(UserCreate user) throws Exception;

    User getUserById(Integer userId) throws Exception;

    User getUserByEmail(String email) throws Exception;

    User getUserByUsername(String username) throws Exception;
}
