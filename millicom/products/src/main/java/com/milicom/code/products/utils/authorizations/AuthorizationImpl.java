package com.milicom.code.products.utils.authorizations;

import com.milicom.code.products.payload.error.ErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.milicom.code.products.utils.Commons.*;


@Service
public class AuthorizationImpl implements Authorization{

    @Override
    public <T> ResponseEntity<?> getResponseValidationService(List<ErrorHandler> errorHandlerList) {
        HttpStatus http = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(buildResponse(http, null, errorHandlerList), http);

    }

    @Override
    public <T> ResponseEntity<?> getResponseIOException(String message) {
        HttpStatus http = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(buildResponse(http, null,
                List.of(new ErrorHandler(http.value(), message))), http);
    }

    @Override
    public <T> ResponseEntity<?> getResponseOk(T data) {
        HttpStatus http = HttpStatus.OK;

        return new ResponseEntity<>(buildResponse(http, data, null), http);
    }
}
