package com.millicom.customer.infocustomer.utils.validations;

import com.millicom.customer.infocustomer.payload.error.ErrorHandler;
import com.millicom.customer.infocustomer.payload.error.ValidationException;
import jakarta.validation.*;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static com.millicom.customer.infocustomer.utils.Commons.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ValidationModelImpl implements ValidationModel {

    @Value("${validator.payload}")
    private String validatorPayload;

    @Override
    public <T> void validateModel(T object) {

        Validator validator = createValidator(this.validatorPayload);
        List<ErrorHandler> errorHandlerList =  new ArrayList<>();

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        for(ConstraintViolation<T> tConstraintViolation : constraintViolations){

            if(isNumeric(tConstraintViolation.getMessage())) {

                AtomicReference<String> field = new AtomicReference<>("");

                tConstraintViolation.getPropertyPath().forEach(node -> {
                    field.set(node.getName());
                });

                errorHandlerList.add(
                        new ErrorHandler(MessagesEnume.findCode(
                                Integer.parseInt(tConstraintViolation.getMessage())
                        ), field.toString())
                );
            }else{
                errorHandlerList.add(new ErrorHandler(
                        MessagesEnume.findCode(MessagesEnume.GENERIC_CODE.getCode()), tConstraintViolation.getMessage()
                ));
            }
        }

        if(!errorHandlerList.isEmpty()) throw new ValidationException(errorHandlerList);

    }

    private static Validator createValidator(String validatorPayload){
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .constraintValidatorPayload(validatorPayload)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        validatorFactory.close();

        return validator;
    }
}
