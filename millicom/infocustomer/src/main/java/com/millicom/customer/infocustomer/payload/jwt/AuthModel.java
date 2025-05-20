package com.millicom.customer.infocustomer.payload.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthModel {

    private final String username;
    private final String password;
}
