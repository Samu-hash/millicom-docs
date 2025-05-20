package com.millicom.customer.infocustomer.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerCreateRequest {

    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String name;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String lastname;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    @Email(message = "1103")
    private String email;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String password;

}
