package com.cars_now.backend.exception;

import com.cars_now.backend.utils.ValidationConst;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception{
    private final ValidationConst validationValue;

    public NotFoundException(final ValidationConst validationConst, final String message) {
        super(message);
        this.validationValue = validationConst;
    }
}
