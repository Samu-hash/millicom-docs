package com.milicom.code.products.payload.error;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorHandler {

    private int code;
    private String message;

    public ErrorHandler(){}

    public ErrorHandler(int code, String message){
        this.code = code;
        this.message = message;
    }

}
