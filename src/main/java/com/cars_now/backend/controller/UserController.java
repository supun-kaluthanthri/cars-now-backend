package com.cars_now.backend.controller;

import com.cars_now.backend.model.*;
import com.cars_now.backend.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.HttpURLConnection;
import java.util.List;

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

    
    @ApiOperation(value = "Gets roles by username", notes = "Enter the username of roles you want to retrieve")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the roles", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the roles"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the role you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The role you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the role")
    })
    @RequestMapping(method =  RequestMethod.GET, value = "/roles/{username}")
    public ResponseEntity<Object> getRolesByUsername(@Valid final @ApiParam(value = "username to received roles", required = true) @PathVariable("username") String username) throws Exception {
        LOGGER.info("get roles by user id API invoked");
        final List<Role> rolesList = userService.getRolesByUserId(username);
        return new ResponseEntity<>(rolesList, HttpStatus.OK);
    }


    @ApiOperation(value = "Gets user by id", notes = "Enter the id of user you want to retrieve")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the user", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the user"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the user you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The user you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the user")
    })
    @RequestMapping(method =  RequestMethod.GET, value = "/{userId}")
    public ResponseEntity<Object> getUserById(@Valid final @ApiParam(value = "username to received roles", required = true) @PathVariable("userId") Integer userId) throws Exception {
        LOGGER.info("get user by user id API invoked");
        final User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @ApiOperation(value = "Gets user by username", notes = "Enter the username of user you want to retrieve")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the user", response = Car.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the user"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the user you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The user you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the user")
    })
    @RequestMapping(method =  RequestMethod.GET, value = "/username/{username}")
    public ResponseEntity<Object> getUserById(@Valid final @ApiParam(value = "username to received roles", required = true) @PathVariable("username") String username) throws Exception {
        LOGGER.info("get user by username API invoked");
        final User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
