package com.cars_now.backend.repository;

import com.cars_now.backend.dto.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findByFeedbackId(Long feedbackId);
}
