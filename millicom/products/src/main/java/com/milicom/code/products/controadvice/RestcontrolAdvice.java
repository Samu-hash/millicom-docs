package com.milicom.code.products.controadvice;

import com.milicom.code.products.utils.authorizations.Authorization;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestcontrolAdvice {

    private final Authorization authorizations;

    private static final Logger logger = LoggerFactory
            .getLogger(RestcontrolAdvice.class);

    @ExceptionHandler({ SQLException.class, DataAccessException.class })
    public ResponseEntity<?> handleDatabaseException(Exception ex) {
        logger.info("---RestcontrolAdvice.handleDatabaseException, {}", ex.toString());
        return authorizations.getResponseIOException(ex.getMessage());
    }

}
