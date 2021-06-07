package com.cars_now.backend.service;

import com.cars_now.backend.model.Feedback;
import com.cars_now.backend.utils.ResultList;

public interface FeedbackService {
    Feedback createFeedback(Feedback feedback) throws Exception;

    Feedback updateFeedback(Feedback feedback, Long feedbackId) throws Exception;

    Feedback getFeedbackById(Long feedbackId) throws Exception;

    ResultList<Feedback> getAllFeedbacks(Integer page, Integer size);

    void deleteFeedback(Long feedbackId) throws Exception;
}
