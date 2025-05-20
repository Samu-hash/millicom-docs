package com.millicom.customer.infocustomer.payload.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomerRequestParams {

    private final String condition;
}
