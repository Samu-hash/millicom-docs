package com.millicom.customer.infocustomer.payload.request.payment;

import lombok.Data;

@Data
public class PaymentProductListRequest {

    private int productId;
    private double price;
    private int quantity;
}
