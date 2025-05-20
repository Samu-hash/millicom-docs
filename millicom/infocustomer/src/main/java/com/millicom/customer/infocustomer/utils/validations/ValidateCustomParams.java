package com.millicom.customer.infocustomer.utils.validations;

import com.millicom.customer.infocustomer.payload.error.ErrorHandler;
import com.millicom.customer.infocustomer.payload.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ValidateCustomParams {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateCustomParams.class);

    public void validateId(Integer id){

        if(Objects.isNull(id) || id == 0) throw new ValidationException(
                List.of(new ErrorHandler(0, "The field id y required")));
    }
}
