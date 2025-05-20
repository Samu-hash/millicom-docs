package com.millicom.customer.infocustomer.payload.request.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Valid
public class PaymentPayRequest {

    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String cardName;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String cardNumber;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String cvv;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String expiry;
}
