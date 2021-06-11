package com.cars_now.backend.controller;

import com.cars_now.backend.model.CarOwner;
import com.cars_now.backend.model.Feedback;
import com.cars_now.backend.service.FeedbackService;
import com.cars_now.backend.utils.ControllerAttributes;
import com.cars_now.backend.utils.ResultList;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.HttpURLConnection;

@RestController
@CrossOrigin
@RequestMapping("/feedbacks")
@Api(description = "crud operations for the feedback", tags = "feedback")
public class FeedbackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    FeedbackService feedbackService;

    @ApiOperation(value = "Creates a feedback", notes = "Enter necessary details to the given attributes to create a new feedback")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully created a new feedback", response = Feedback.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid | Empty Input Parameters in the Request"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to create the new feedback"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Creating the particular feedback is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while creating the new feedback")
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createFeedback(final @ApiParam(value = "Enter necessary details to create a new feedback", required = true) @RequestBody Feedback feedback) throws Exception {
        LOGGER.trace("Create feedback api invoked");
        final Feedback createdFeedback = feedbackService.createFeedback(feedback);
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Updates a feedback", notes = "Enter the feedback id of a particular feedback you want to update")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully updated the feedback", response = Feedback.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to update the feedback"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Updating the particular feedback is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The feedback you were trying to update is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while updating the feedback")
    })
    @RequestMapping(value = "/{feedbackId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateFeedback(@Valid final @RequestBody Feedback feedback, final @ApiParam(value = "feedback id to update", required = true) @PathVariable("feedbackId") Long feedbackId) throws Exception {
        LOGGER.info("Update feedback api invoked. ");

        final Feedback updateFeedback = feedbackService.updateFeedback(feedback, feedbackId);
        return new ResponseEntity<>(updateFeedback, HttpStatus.OK);
    }


    @ApiOperation(value = "Gets a feedback", notes = "Enter the feedback id of a particular feedback you want to retrieve")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the feedback", response = Feedback.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the feedback"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the feedback you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The feedback you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the feedback")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{feedbackId}")
    public ResponseEntity<Object> getFeedbackById( @Valid final @ApiParam(value = "feedback id to retrieve", required = true) @PathVariable("feedbackId") Long feedbackId) throws Exception {
        LOGGER.info("Get carOwner API invoked");

        final Feedback feedbackResponse = feedbackService.getFeedbackById(feedbackId);
        return new ResponseEntity<>(feedbackResponse, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all the feedbacks", notes = "Will retrieve a list of all the available feedbacks")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully retrieved the feedbacks list", response = Feedback.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the feedbacks list"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the feedbacks list you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The feedbacks list you were trying to reach is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while retrieving the feedbacks list")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAllFeedbacks(final @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  final @RequestParam(name = "size", defaultValue = "10") Integer size) throws Exception {
        LOGGER.info("Get all feedbacks API invoked");

        final ResultList<Feedback> feedbackList = feedbackService.getAllFeedbacks(page, size);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ControllerAttributes.ACCESS_CONTROL_HEADERS.tag(), ControllerAttributes.X_TOTAL_COUNT.tag());
        responseHeaders.set(ControllerAttributes.X_TOTAL_COUNT.tag(), String.valueOf(feedbackList.getTotalCount()));
        return new ResponseEntity<>(feedbackList, responseHeaders, HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a feedback", notes = "Enter the feedback id of a particular feedback you want to delete")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully deleted the feedback", response = Feedback.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to delete the feedback"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Deleting the particular feedback is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "The feedback you were trying to delete is not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error occurred while deleting the feedback")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{feedbackId}")
    public ResponseEntity<Object> deleteFeedback(final @ApiParam(value = "feedback id you want to delete", required = true) @PathVariable("feedbackId") Long feedbackId) throws Exception {
        LOGGER.info("Delete feedback API invoked");

        feedbackService.deleteFeedback(feedbackId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

