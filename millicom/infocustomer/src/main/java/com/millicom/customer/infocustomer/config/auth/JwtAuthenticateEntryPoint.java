package com.millicom.customer.infocustomer.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.millicom.customer.infocustomer.context.AuthDataContext;
import com.millicom.customer.infocustomer.context.ErrorContext;
import com.millicom.customer.infocustomer.payload.response.GeneralResponse;
import com.millicom.customer.infocustomer.utils.Commons;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticateEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticateEntryPoint.class);


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        logger.info("-------- Configurando mensajes de excepción en provider");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String message = Objects.nonNull(ErrorContext.getContextError())
                ? ErrorContext.getContextError().getMessage()
                : Objects.nonNull(authException) ? authException.getMessage(): "Error";
        GeneralResponse<?,?> res = Commons.buildResponseFilter(message);

        ErrorContext.remove();
        AuthDataContext.remove();
        OBJ_MAPPER.writeValue(response.getOutputStream(),res);
    }
}
