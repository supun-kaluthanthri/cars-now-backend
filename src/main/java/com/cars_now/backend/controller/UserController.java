package com.cars_now.backend.controller;

import com.cars_now.backend.model.Feedback;
import com.cars_now.backend.model.User;
import com.cars_now.backend.model.UserCreate;
import com.cars_now.backend.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

@CrossOrigin
@RequestMapping("/users")
@Api(description = "crud operations for the user", tags = "user")
@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creates a user", notes = "Enter necessary details to the given attributes to create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully created a new user", response = Feedback.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid | Empty Input Parameters in the Request"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to create the new user"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Creating the particular user is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while creating the new user")
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(final @ApiParam(value = "Enter necessary details to create a new user", required = true) @RequestBody UserCreate user) throws Exception {
        LOGGER.trace("Create user api invoked");
        final User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String getTest(){
        return "saaa";
    }

}
