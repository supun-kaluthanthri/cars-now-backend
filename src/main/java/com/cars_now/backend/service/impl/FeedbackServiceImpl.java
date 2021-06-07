package com.cars_now.backend.service.impl;

import com.cars_now.backend.dto.Renter;
import com.cars_now.backend.exception.NotFoundException;
import com.cars_now.backend.model.Feedback;
import com.cars_now.backend.repository.FeedbackRepository;
import com.cars_now.backend.repository.RenterRepository;
import com.cars_now.backend.service.FeedbackService;
import com.cars_now.backend.utils.DtoToResponseConverter;
import com.cars_now.backend.utils.ResultList;
import com.cars_now.backend.utils.ValidationConst;
import com.cars_now.backend.utils.modelConverters.FeedbackDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    FeedbackDtoConverter feedbackDtoConverter;

    @Autowired
    RenterRepository renterRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    DtoToResponseConverter dtoToResponseConverter;

    @Override
    public Feedback createFeedback(final Feedback feedback) throws Exception {

        final Renter renter = renterRepository.findByRenterId(feedback.getRenterId());
        if (renter == null) {
            throw new NotFoundException(ValidationConst.RENTER_NOT_FOUND, ValidationConst.RENTER_NOT_FOUND.message());
        }

        final com.cars_now.backend.dto.Feedback createdFeedback = feedbackRepository.save(feedbackDtoConverter.feedbackCreateRequestToFeedbackDto(feedback, renter));

        return dtoToResponseConverter.feedBackDtoToFeedbackResponse(createdFeedback);
    }

    @Override
    public Feedback updateFeedback(final Feedback feedback, final Long feedbackId) throws Exception {
        com.cars_now.backend.dto.Feedback repoFeedback = feedbackRepository.findByFeedbackId(feedbackId);

        if(repoFeedback == null) {
            throw new NotFoundException(ValidationConst.FEEDBACK_NOT_FOUND, ValidationConst.FEEDBACK_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + feedbackId);
        }

        final com.cars_now.backend.dto.Feedback updatedFeedback =  feedbackRepository.
                save(feedbackDtoConverter.feedbackUpdateRequestToFeedbackDto(feedback, repoFeedback));

        return  dtoToResponseConverter.feedBackDtoToFeedbackResponse(updatedFeedback);
    }

    @Override
    public Feedback getFeedbackById(final Long feedbackId) throws Exception{
        final com.cars_now.backend.dto.Feedback feedback = feedbackRepository.findByFeedbackId(feedbackId);

        if(feedback == null) {
            throw new NotFoundException(ValidationConst.FEEDBACK_NOT_FOUND, ValidationConst.FEEDBACK_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + feedbackId);
        }

        return dtoToResponseConverter.feedBackDtoToFeedbackResponse(feedback);
    }


    @Override
    public ResultList<Feedback> getAllFeedbacks(Integer page, Integer size){

        final ResultList<Feedback> feedbackResultList = new ResultList<>();
        final List<Feedback> feedbackList = new ArrayList<>();
        final Page<com.cars_now.backend.dto.Feedback> feedbackListItr =  feedbackRepository.
                findAll(PageRequest.of(page,size));

        for (com.cars_now.backend.dto.Feedback feedback : feedbackListItr){
            feedbackList.add(dtoToResponseConverter.feedBackDtoToFeedbackResponse(feedback));
        }

        feedbackResultList.setList(feedbackList);
        feedbackResultList.setTotalCount(feedbackList.size());

        return feedbackResultList;
    }

    @Override
    public void deleteFeedback(final Long feedbackId) throws Exception {
        final com.cars_now.backend.dto.Feedback feedback = feedbackRepository.findByFeedbackId(feedbackId);

        if (feedback != null) {
            feedbackRepository.delete(feedback);
        } else {
            throw new NotFoundException(ValidationConst.FEEDBACK_NOT_FOUND, ValidationConst.FEEDBACK_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + feedbackId);
        }
    }
}
