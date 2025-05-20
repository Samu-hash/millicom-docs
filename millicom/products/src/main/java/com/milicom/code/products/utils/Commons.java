package com.milicom.code.products.utils;

import com.milicom.code.products.payload.error.ErrorHandler;
import com.milicom.code.products.payload.response.GeneralResponse;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Commons {

    public static final Map<String, Predicate<String>> HEADER_VALIDATORS = Map.of(
            "X-User-App", "my-app-millicom"::equals,
            "X-App-Version", value -> value.matches("^\\d+\\.\\d+-[a-zA-Z0-9]+$"
            ),
            "X-Location", value -> List.of("SV").contains(value.toUpperCase()),
            "Authorization", value -> value != null && value.startsWith("Bearer ")
    );

    public static <T> Object buildResponse(HttpStatus http, T obj, List<ErrorHandler> errors){
        GeneralResponse<T> generalResponse = new GeneralResponse<>();

        generalResponse.setCode(http.value());
        generalResponse.setMessage(http.name());
        generalResponse.setData(obj);
        generalResponse.setErrors(errors);

        return generalResponse;
    }
}
