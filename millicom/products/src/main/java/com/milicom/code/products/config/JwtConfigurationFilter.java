package com.milicom.code.products.config;

import com.milicom.code.products.context.ErrorContext;
import com.milicom.code.products.payload.error.ErrorHandler;
import com.milicom.code.products.utils.Commons;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class JwtConfigurationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtConfigurationFilter.class);
    private final JwtConfigurationEntryPoint jwtConfigurationEntryPoint;
    private final TokenServiceProvider tokenServiceProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("---------JwtConfigurationFilter.doFilterInternal... ingresando al primer filtro, el punto de acceso");
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);



        logger.info("---------JwtConfigurationFilter.doFilterInternal... validando header {}", token);
        if (!Objects.isNull(token) && !token.isEmpty()) {
            String[] split = token.split(" ");

            logger.info("----JwtConfigurationFilter.doFilterInternal validando split {}", Arrays.stream(split).toArray());

            if (split.length == 2 && "Bearer".equals(split[0])) {

                try {
                    logger.info("----JwtConfigurationFilter.doFilterInternal intenta validar el token");

                    validateHeaders(request, response);

                    SecurityContextHolder.getContext().setAuthentication(
                            tokenServiceProvider.validateToken(split[1])
                    );

                } catch (Exception e) {
                    logger.info("----JwtConfigurationFilter.doFilterInternal fallo en validacion de token {}", e.getMessage());
                    SecurityContextHolder.clearContext();
                    ErrorContext.setContextError(new ErrorHandler(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
                    jwtConfigurationEntryPoint.commence(request, response, null);
                }
            }else{
                ErrorContext.setContextError(new ErrorHandler(HttpStatus.UNAUTHORIZED.value(), "token does not meet the expected configuration"));
                jwtConfigurationEntryPoint.commence(request, response, null);
            }
        }else{
            ErrorContext.setContextError(new ErrorHandler(HttpStatus.UNAUTHORIZED.value(), "the token is invalid"));
            jwtConfigurationEntryPoint.commence(request, response, null);
        }
        logger.info("---------JwtConfigurationFilter.doFilterInternal... end");
        filterChain.doFilter(request, response);
    }


    private void validateHeaders(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (Map.Entry<String, Predicate<String>> entry : Commons.HEADER_VALIDATORS.entrySet()) {
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
