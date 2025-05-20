package com.millicom.customer.infocustomer.service;

import com.millicom.customer.infocustomer.payload.error.ErrorHandler;
import com.millicom.customer.infocustomer.payload.error.ValidationException;
import com.millicom.customer.infocustomer.payload.mapper.CustomerRequestToCustomer;
import com.millicom.customer.infocustomer.payload.model.CustomerModel;
import com.millicom.customer.infocustomer.payload.request.CustomerCreateRequest;
import com.millicom.customer.infocustomer.payload.request.CustomerRequest;
import com.millicom.customer.infocustomer.payload.request.CustomerRequestParams;
import com.millicom.customer.infocustomer.payload.request.LoginRequest;
import com.millicom.customer.infocustomer.service.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements  CustomerService {

    private final CustomerRepository customerRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public List<CustomerModel> findAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerModel findByParam(CustomerRequestParams requestParams) {
        LOGGER.info("conditions {}", requestParams.getCondition());
        return customerRepository.findByCondition(requestParams.getCondition());
    }

    @Override
    public CustomerModel saveValues(CustomerRequest customerRequest, String token) throws ValidationException{

        LOGGER.info("entro a guardar. token {}", token);
        if(Objects.isNull(token) || token.isBlank())
            throw new ValidationException(List.of(new ErrorHandler(0, "Api-Header is required")));
        String[] userSplit = token.split(":");

        LOGGER.info("entro a guardar. mapper {} split {}", customerRequest, userSplit[0]);
        CustomerModel model = CustomerRequestToCustomer.INSTANCE.classToModel(customerRequest);

        LOGGER.info("entro a guardar. model {}", model);
        return customerRepository.save(model);
    }

    @Override
    public CustomerModel createAccount(CustomerCreateRequest customerRequest) {
        LOGGER.info("entro a guardar. mapper {}", customerRequest);
        CustomerModel model = CustomerRequestToCustomer
                .INSTANCE.classToModelCreate(customerRequest);

        LOGGER.info("-----entro a guardar. {}", model);
        return customerRepository.save(model);
    }

    @Override
    public CustomerModel updateValues(CustomerRequest customerRequest) {

        if(Objects.isNull(customerRequest.getId()) || customerRequest.getId() == 0)
            throw new ValidationException(List.of(new ErrorHandler(0, "Identifier is required")));

        CustomerModel model = CustomerRequestToCustomer.INSTANCE.classToModel(customerRequest);
        return customerRepository.save(model);
    }

    @Override
    public int changeStatusUser(CustomerRequestParams requestParams) {
        customerRepository.updateFields(requestParams.getCondition());
        return 1;
    }

    @Override
    public CustomerModel login(LoginRequest request) {
        CustomerModel customerModel = customerRepository
                .createLogin(request.getUsername(), request.getPassword());

        if(Objects.isNull(customerModel))
            throw new ValidationException(List.of(new ErrorHandler(102, "No data found")));
        return customerModel;
    }

}
