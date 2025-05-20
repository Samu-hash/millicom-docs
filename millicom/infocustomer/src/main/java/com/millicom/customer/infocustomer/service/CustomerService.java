package com.millicom.customer.infocustomer.service;

import com.millicom.customer.infocustomer.payload.model.CustomerModel;
import com.millicom.customer.infocustomer.payload.request.CustomerCreateRequest;
import com.millicom.customer.infocustomer.payload.request.CustomerRequest;
import com.millicom.customer.infocustomer.payload.request.CustomerRequestParams;
import com.millicom.customer.infocustomer.payload.request.LoginRequest;

import java.util.List;

public interface CustomerService {

    List<CustomerModel> findAllCustomer();

    CustomerModel findByParam(CustomerRequestParams requestParams);

    CustomerModel saveValues(CustomerRequest customerRequest, String token);

    CustomerModel createAccount(CustomerCreateRequest customerRequest);

    CustomerModel updateValues(CustomerRequest customerRequest);

    int changeStatusUser(CustomerRequestParams requestParams);

    CustomerModel login(LoginRequest request);
}
