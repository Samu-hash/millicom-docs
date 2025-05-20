package com.millicom.customer.infocustomer.controller;

import com.millicom.customer.infocustomer.config.auth.TokenServiceProvider;
import com.millicom.customer.infocustomer.payload.request.LoginRequest;
import com.millicom.customer.infocustomer.service.CustomerService;
import com.millicom.customer.infocustomer.utils.authorization.Authorization;
import com.millicom.customer.infocustomer.utils.validations.ValidationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/access")
@RequiredArgsConstructor
public class LoginController {

    private final Authorization authorization;
    private final TokenServiceProvider tokenServiceProvider;
    private final CustomerService customerService;
    private final ValidationModel validationModel;

    @GetMapping("/token")
    public ResponseEntity<?> token(){
        return authorization.getResponseOk(tokenServiceProvider.createToken());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        validationModel.validateModel(request);

        return authorization.getResponseOk(customerService.login(request));
    }
}
