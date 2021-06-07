package com.cars_now.backend.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Feedback {
    private Long feedbackId;
    private Long renterId;
    private String message;
}
