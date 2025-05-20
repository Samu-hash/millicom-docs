package com.millicom.customer.infocustomer.utils;

import com.millicom.customer.infocustomer.payload.error.ErrorHandler;
import com.millicom.customer.infocustomer.payload.response.GeneralResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class Commons {

    public static Map<String, Predicate<String>> buildValidators(boolean validatePass) {
        Map<String, Predicate<String>> validators = new HashMap<>();

        validators.put("X-User-App", "my-app-millicom"::equals);
        validators.put("X-App-Version", value -> value.matches("^\\d+\\.\\d+-[a-zA-Z0-9]+$"));

        validators.put("X-Location", value -> List.of("SV").contains(value.toUpperCase()));
        if (validatePass)
            validators.put("X-Pass-App", "MY-P@ssW0rd-With.-Token"::equals);

        return validators;
    }


    public static final String[] PATH_RELATIVE_ACCESS = {"/access/token"};

    public static boolean isNumeric(String value){
        return (!Objects.isNull(value) && value.matches("\\d+"));
    }

    public static <T, E> Object buildResponse(HttpStatus http, T obj, E errors){
        GeneralResponse<T, E> generalResponse = new GeneralResponse<>();

        generalResponse.setCode(http.value());
        generalResponse.setMessage(http.name());
        generalResponse.setData(obj);
        generalResponse.setErrors(errors);

        return generalResponse;
    }

    public static GeneralResponse<?,?> buildResponseFilter(String message){
        GeneralResponse<Object, List<ErrorHandler>> build = new GeneralResponse<>();

        HttpStatus http = HttpStatus.BAD_REQUEST;

        build.setCode(http.value());
        build.setMessage(http.getReasonPhrase());
        build.setErrors(List.of(new ErrorHandler(0, message)));

        return build;
    }

    public static void validateHeaders(HttpServletRequest request, HttpServletResponse response, boolean validatePass) throws Exception {
        for (Map.Entry<String, Predicate<String>> entry : Commons.buildValidators(validatePass).entrySet()) {
            String header = entry.getKey();
            String value = request.getHeader(header);
            if (value == null || value.trim().isEmpty()) {
                throw new Exception("Missing required header: " + header);
            }

            Predicate<String> validator = entry.getValue();
            if (!validator.test(value)) {
                throw new Exception("Invalid value for header: " + header);
            }
        }
    }
}
