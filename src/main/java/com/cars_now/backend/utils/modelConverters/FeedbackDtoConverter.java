package com.cars_now.backend.utils.modelConverters;

import com.cars_now.backend.dto.Renter;
import com.cars_now.backend.exception.NotFoundException;
import com.cars_now.backend.model.CarOwner;
import com.cars_now.backend.model.Feedback;
import com.cars_now.backend.utils.ValidationConst;
import org.springframework.stereotype.Component;

@Component
public class FeedbackDtoConverter {

    public com.cars_now.backend.dto.Feedback feedbackCreateRequestToFeedbackDto(final Feedback feedback,
                                                                                final Renter renter) {
        com.cars_now.backend.dto.Feedback repoFeedback = new com.cars_now.backend.dto.Feedback();

        repoFeedback.setMessage(feedback.getMessage());
        repoFeedback.setRating(feedback.getRating());
        repoFeedback.setRenter(renter);
        return repoFeedback;
    }

    public com.cars_now.backend.dto.Feedback feedbackUpdateRequestToFeedbackDto(final Feedback feedback,
                                                                                final com.cars_now.backend.dto.Feedback repoFeedback) {

        repoFeedback.setMessage(feedback.getMessage());
        repoFeedback.setRating(feedback.getRating());
        return repoFeedback;
    }



}
