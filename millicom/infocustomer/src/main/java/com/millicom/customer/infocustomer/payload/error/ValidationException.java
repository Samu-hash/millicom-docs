package com.millicom.customer.infocustomer.payload.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class ValidationException extends RuntimeException {

    private final transient List<ErrorHandler> errors;
}
