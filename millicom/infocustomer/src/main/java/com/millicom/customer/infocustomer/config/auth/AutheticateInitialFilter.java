package com.millicom.customer.infocustomer.config.auth;

import com.millicom.customer.infocustomer.context.AuthDataContext;
import com.millicom.customer.infocustomer.context.ErrorContext;
import com.millicom.customer.infocustomer.payload.error.ErrorHandler;
import com.millicom.customer.infocustomer.payload.jwt.AuthModel;
import com.millicom.customer.infocustomer.utils.Commons;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AutheticateInitialFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AutheticateInitialFilter.class);

    private final JwtAuthenticateEntryPoint jwtAuthenticateEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean isValidPath = Arrays.stream(Commons.PATH_RELATIVE_ACCESS)
                .map(AutheticateInitialFilter::normalize)
                .toList().contains(request.getServletPath());

        logger.info("---------AutheticateInitialFilter.doFilterInternal... path valid {}", isValidPath);

        if(isValidPath && HttpMethod.GET.matches(request.getMethod())){
            logger.info("---------AutheticateInitialFilter.doFilterInternal... path valid");

            logger.info("---------AutheticateInitialFilter.doFilterInternal... init validation user");
            try {
                Commons.validateHeaders(request, response, true);

                String username = request.getHeader("X-User-App");
                String password = request.getHeader("X-Pass-App");

                AuthModel authModel = new AuthModel(username, password);
                logger.info("---------AutheticateInitialFilter.doFilterInternal - end {}", authModel);
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                authModel, null, Collections.emptyList())
                );

                AuthDataContext.setDataStringContext(authModel);
                logger.info("---------AutheticateInitialFilter.doFilterInternal - end");

            } catch (Exception e) {
                logger.info("----JwtConfigurationFilter.doFilterInternal fallo en validacion de token {}", e.getMessage());
                SecurityContextHolder.clearContext();
                ErrorContext.setContextError(new ErrorHandler(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
                jwtAuthenticateEntryPoint.commence(request, response, null);
            }
        }

        filterChain.doFilter(request, response);
    }

    private static String normalize(String path) {
        return path.replaceFirst("^/api/v1/customer", "");
    }


}
