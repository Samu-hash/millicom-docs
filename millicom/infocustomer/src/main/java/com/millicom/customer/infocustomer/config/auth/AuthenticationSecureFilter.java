package com.millicom.customer.infocustomer.config.auth;

import com.millicom.customer.infocustomer.context.ErrorContext;
import com.millicom.customer.infocustomer.payload.error.ErrorHandler;
import com.millicom.customer.infocustomer.utils.Commons;
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
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationSecureFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSecureFilter.class);

    private final TokenServiceProvider tokenServiceProvider;
    private final JwtAuthenticateEntryPoint jwtAuthenticateEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("---------AuthenticationSecureFilter.doFilterInternal... ingresando al primer filtro, el punto de acceso");
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        logger.info("---------AuthenticationSecureFilter.doFilterInternal... validando header {}", header);

        if (!Objects.isNull(header) && !header.isEmpty()) {
            String[] split = header.split(" ");

            if (split.length == 2 && "Bearer".equals(split[0])) {

                logger.info("----AuthenticationSecureFilter.doFilterInternal validando split {}", Arrays.stream(split).toArray());

                try {
                    logger.info("----wtAuthenticationFilter.doFilterInternal intenta validar el token");

                    Commons.validateHeaders(request, response, false);

                    SecurityContextHolder.getContext().setAuthentication(
                            tokenServiceProvider.validateToken(split[1])
                    );
                } catch (Exception e) {
                    logger.info("----wtAuthenticationFilter.doFilterInternal fallo en validacion de token {}", e.getMessage());
                    SecurityContextHolder.clearContext();
                    ErrorContext.setContextError(new ErrorHandler(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
                    jwtAuthenticateEntryPoint.commence(request, response, null);
                }
            }
        }
        logger.info("---------AuthenticationSecureFilter.doFilterInternal... end");
        filterChain.doFilter(request, response);
    }
}
