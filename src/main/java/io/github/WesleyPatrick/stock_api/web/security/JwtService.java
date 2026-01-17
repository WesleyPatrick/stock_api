package io.github.WesleyPatrick.stock_api.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.WesleyPatrick.stock_api.domain.model.User;
import io.github.WesleyPatrick.stock_api.infra.config.props.JwtProps;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    JwtProps jwtProps;
    Algorithm algorithm;
    JWTVerifier jwtVerifier;

    public JwtService(JwtProps jwtProps,  Algorithm algorithm, JWTVerifier jwtVerifier) {
        this.jwtProps = jwtProps;
        this.algorithm = algorithm;
        this.jwtVerifier = jwtVerifier;
    }

    public String generateToken(User user) {

        Instant now = Instant.now();
        Instant expiresIn = now.plus(jwtProps.accessMinutes(), ChronoUnit.MINUTES);

        return JWT.create()
                .withClaim("userId", String.valueOf(user.getId()))
                .withIssuer(jwtProps.issuer())
                .withSubject(user.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(expiresIn)
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) {
        return jwtVerifier.verify(token);
    }

    public String generateRefreshToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(jwtProps.accessDays(), ChronoUnit.DAYS);

        return JWT.create()
                .withIssuer(jwtProps.issuer())
                .withSubject(user.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withClaim("userId", user.getId().toString())
                .withClaim("type", "refresh")
                .sign(algorithm);
    }

    public boolean isRefreshToken(DecodedJWT jwt) {
        return "refresh".equals(jwt.getClaim("type").asString());
    }

}
