package com.milicom.code.products.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TokenServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceProvider.class);

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    public Authentication validateToken(String token) {
        logger.info("-------iniciando de la validacion de token");
        Algorithm alg = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(alg).build();

        DecodedJWT decoded = verifier.verify(token);
        logger.info("Token válido. Issuer: {}", decoded.getIssuer());
        return new UsernamePasswordAuthenticationToken(decoded.getIssuer(), null, Collections.emptyList());
    }
}
