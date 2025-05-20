package com.millicom.customer.infocustomer.payload.error;

import com.millicom.customer.infocustomer.utils.validations.MessagesEnume;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorHandler {

    private int code;
    private String message;

    public ErrorHandler(){}

    public ErrorHandler(MessagesEnume enume, String field){
        this.code = enume.getCode();
        this.message = String.format(enume.getMessage(), field);
    }

    public ErrorHandler(int code, String message){
        this.code = code;
        this.message = message;
    }

}
