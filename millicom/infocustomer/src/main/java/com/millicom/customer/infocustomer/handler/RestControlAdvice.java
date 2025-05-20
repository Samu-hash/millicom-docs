package com.millicom.customer.infocustomer.handler;

import com.millicom.customer.infocustomer.payload.error.ValidationException;
import com.millicom.customer.infocustomer.utils.authorization.Authorization;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@RequiredArgsConstructor
public class RestControlAdvice {

    private final Authorization authorization;
    private static final Logger LOGGER = LoggerFactory.getLogger(RestControlAdvice.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handlerValidation(ValidationException validationException){

        LOGGER.info("[{}.{}] validation error: {}", getCallerClassName(), getCallerMethodName(), validationException.getErrors());
        return authorization.getResponseValidationService(validationException.getErrors());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handlerIOException(IOException e){

        LOGGER.info("[{}.{}] IOException error: {}", getCallerClassName(), getCallerMethodName(), e.getMessage());
        return authorization.getResponseIOException(e.getMessage());
    }


    private String getCallerClassName(){
        return Thread.currentThread().getStackTrace()[3].getClassName();
    }

    private String getCallerMethodName(){
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }
}
