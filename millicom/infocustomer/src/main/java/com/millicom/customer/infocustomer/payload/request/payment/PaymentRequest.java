package com.millicom.customer.infocustomer.payload.request.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {

    @NotNull(message = "1100")
    private int idUser;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String address;
    @NotNull(message = "1103")
    @Valid
    private PaymentPayRequest payment;
    @NotNull(message = "1103")
    @NotEmpty(message = "1104")
    @Valid
    private List<PaymentProductListRequest> details;
}
