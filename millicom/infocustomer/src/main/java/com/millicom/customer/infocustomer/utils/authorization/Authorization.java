package com.millicom.customer.infocustomer.utils.authorization;

import com.millicom.customer.infocustomer.payload.error.ErrorHandler;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Authorization {

    <T>ResponseEntity<?> getResponseValidationService(List<ErrorHandler> errorHandlerList);

    <T>ResponseEntity<?> getResponseIOException(String message);

    <T>ResponseEntity<?> getResponseOk(T data);

}
