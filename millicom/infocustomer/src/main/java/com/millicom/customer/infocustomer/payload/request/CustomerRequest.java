package com.millicom.customer.infocustomer.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerRequest {

    private Integer identity;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String name;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String lastname;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String address;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    @Email(message = "1103")
    private String email;
    @NotNull(message = "1100")
    @NotBlank(message = "1101")
    private String password;
    @Pattern(regexp = "\\d{16}", message = "The field creditCardNumber must be 16 digits long")
    private String creditCard;
    @Pattern(regexp = "\\d{3}", message = "The field cardCvv must be 3 digits long")
    private String creditCvv;
    private String usernameManager;
}
