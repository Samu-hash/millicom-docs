package com.millicom.customer.infocustomer.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String username;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String password;
}
