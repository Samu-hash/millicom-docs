package com.millicom.customer.infocustomer.controller;

import com.millicom.customer.infocustomer.payload.request.CustomerCreateRequest;
import com.millicom.customer.infocustomer.payload.request.CustomerRequest;
import com.millicom.customer.infocustomer.payload.request.CustomerRequestParams;
import com.millicom.customer.infocustomer.payload.request.payment.PaymentRequest;
import com.millicom.customer.infocustomer.service.CustomerService;
import com.millicom.customer.infocustomer.utils.authorization.Authorization;
import com.millicom.customer.infocustomer.utils.validations.ValidateCustomParams;
import com.millicom.customer.infocustomer.utils.validations.ValidationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CustomerController {

    private static final String PATH_INFO = "/information";
    private final CustomerService customerService;
    private final ValidateCustomParams validateCustomParams;
    private final ValidationModel validationModel;
    private final Authorization authorization;

    @GetMapping(PATH_INFO)
    public ResponseEntity<?> getInformationCustomer(){

        return authorization.getResponseOk(customerService.findAllCustomer());
    }

    @GetMapping(PATH_INFO+"/{customParam}")
    public ResponseEntity<?> getInformationByCustomParams(@PathVariable String customParam){

        return authorization.getResponseOk(customerService
                .findByParam(new CustomerRequestParams(customParam)));
    }

    @PostMapping(PATH_INFO+"/save-data")
    public ResponseEntity<?> saveValues(@RequestBody CustomerRequest request){

        validationModel.validateModel(request);

        return authorization.getResponseOk(
          customerService.saveValues(request, "APiUsername:APiPass")
        );
    }

    @PostMapping(PATH_INFO+"/create-accunt")
    public ResponseEntity<?> createAccount(@RequestBody CustomerCreateRequest request){

        validationModel.validateModel(request);

        return authorization.getResponseOk(
                customerService.createAccount(request)
        );
    }

    @PostMapping(PATH_INFO+"/update-data")
    public ResponseEntity<?> updateData(@RequestBody CustomerRequest request){

        validationModel.validateModel(request);

        validateCustomParams.validateId(request.getId());

        return authorization.getResponseOk(
                customerService.updateValues(request)
        );
    }

    @PostMapping(PATH_INFO+"/save-payment")
    public ResponseEntity<?> savePayment(@RequestBody PaymentRequest request){

        validationModel.validateModel(request);

        return authorization.getResponseOk(
                customerService.updateValues(request)
        );
    }
}
