package com.millicom.customer.infocustomer.payload.request.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Valid
public class PaymentProductListRequest {

    @NotNull(message = "1100")
    private int productId;
    @NotNull(message = "1100")
    private double price;
    @NotNull(message = "1100")
    private int quantity;
}
