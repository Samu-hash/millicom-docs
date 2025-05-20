package com.milicom.code.products.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milicom.code.products.context.ErrorContext;
import com.milicom.code.products.payload.error.ErrorHandler;
import com.milicom.code.products.payload.response.GeneralResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class JwtConfigurationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JwtConfigurationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        logger.info("-------- config mjs exception");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String message = Objects.nonNull(ErrorContext.getContextError())
                ? ErrorContext.getContextError().getMessage()
                : Objects.nonNull(authException) ? authException.getMessage(): "Error";

        GeneralResponse<?> resp = new GeneralResponse<>();
        resp.setCode(HttpStatus.UNAUTHORIZED.value());
        resp.setMessage(message);
        resp.setErrors(List.of(ErrorContext.getContextError()));

        logger.info("-------- trace error {}, message-request {}", resp.getErrors(), message);
        ErrorContext.remove();
        OBJ_MAPPER.writeValue(response.getOutputStream(), resp);

    }
}
