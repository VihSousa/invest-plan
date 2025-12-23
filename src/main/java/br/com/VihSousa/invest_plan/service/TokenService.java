package br.com.vihsousa.invest_plan.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import br.com.vihsousa.invest_plan.model.User;

@Service
public class TokenService {

    // Gets the secret key defined in application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    // 1. GENERATE TOKEN
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("invest-plan-api") // The issuer of the token (our API)
                    .withSubject(user.getEmail())  // The owner of the token (we save the email)
                    .withExpiresAt(genExpirationDate()) // Expiration time
                    .sign(algorithm); // Digitally signs the token
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    // 2. VALIDATE TOKEN
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("invest-plan-api")
                    .build()
                    .verify(token) // Decrypts and verifies the signature
                    .getSubject(); // If valid, returns the email stored inside
        } catch (JWTVerificationException exception){
            return ""; // If token is invalid or expired, returns empty string
        }
    }

    // Defines token expiration time (2 hours, timezone -03:00)
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}