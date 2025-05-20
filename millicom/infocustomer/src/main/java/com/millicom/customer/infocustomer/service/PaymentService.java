package com.millicom.customer.infocustomer.service;

import com.millicom.customer.infocustomer.payload.request.payment.PaymentRequest;

public interface PaymentService {

    int processPayment(PaymentRequest request);
}
