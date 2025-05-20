package com.millicom.customer.infocustomer.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class GeneralResponse<R, E>{

    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private R data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private E errors;
}
