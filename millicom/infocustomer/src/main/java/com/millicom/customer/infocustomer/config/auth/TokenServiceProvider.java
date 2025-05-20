package com.millicom.customer.infocustomer.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.millicom.customer.infocustomer.context.AuthDataContext;
import com.millicom.customer.infocustomer.payload.jwt.AuthModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;

@Component
public class TokenServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceProvider.class);

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.time}")
    private Integer intervalTime;

    public String createToken() {
        logger.info("-------parametrizando variables de token");

        ZoneId zone = ZoneId.of("America/El_Salvador");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limit = now.plusMinutes(intervalTime);

        ZonedDateTime zonedDateTimeNow = now.atZone(zone);
        ZonedDateTime zonedDateTimeLimit = limit.atZone(zone);

        Algorithm alg = Algorithm.HMAC256(this.secretKey);

        AuthModel values = AuthDataContext.getDataStringContext();

        logger.info("-------iniciando la creacion de token");
        return "Bearer ".concat(JWT.create().withIssuer(values.getUsername())
                .withSubject(values.getPassword())
                .withIssuedAt(Date.from(zonedDateTimeNow.toInstant()))
                .withExpiresAt(Date.from(zonedDateTimeLimit.toInstant()))
                .withClaim("role", "admin")
                .sign(alg));
    }

    public Authentication validateToken(String token) {
        logger.info("-------iniciando de la validacion de token");
        Algorithm alg = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(alg).build();

        DecodedJWT decoded = verifier.verify(token);
        logger.info("Token válido. Issuer: {}", decoded.getIssuer());
        return new UsernamePasswordAuthenticationToken(decoded.getIssuer(), null, Collections.emptyList());
    }
}
