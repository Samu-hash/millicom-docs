package com.millicom.customer.infocustomer.payload.request.payment;

import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {

    private int idUser;
    private String address;
    private PaymentPayRequest payment;
    private List<PaymentProductListRequest> details;
}
