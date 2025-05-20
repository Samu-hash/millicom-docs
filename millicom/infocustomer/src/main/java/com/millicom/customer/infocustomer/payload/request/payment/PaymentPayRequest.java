package com.millicom.customer.infocustomer.payload.request.payment;

import lombok.Data;

@Data
public class PaymentPayRequest {

    private String cardName;
    private String cardNumber;
    private String cvv;
    private String expiry;
}
