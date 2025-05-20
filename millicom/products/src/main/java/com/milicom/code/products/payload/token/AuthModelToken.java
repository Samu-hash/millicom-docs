package com.milicom.code.products.payload.token;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthModelToken {
    private String username;
    private String password;
}
