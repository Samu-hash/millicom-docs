package com.milicom.code.products.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.milicom.code.products.payload.error.ErrorHandler;
import lombok.Data;

import java.util.List;

@Data
public class GeneralResponse<T>{

    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorHandler> errors;
}
