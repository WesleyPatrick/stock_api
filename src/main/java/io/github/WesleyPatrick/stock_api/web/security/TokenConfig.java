package io.github.WesleyPatrick.stock_api.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.WesleyPatrick.stock_api.infra.config.props.JwtProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProps.class)
public class TokenConfig {

    @Bean
    Algorithm algorithm(JwtProps jwtProps) {
        return Algorithm.HMAC256(jwtProps.secret());
    }

    @Bean
    JWTVerifier jwtVerifier(JwtProps jwtProps) {
        return JWT.require(algorithm(jwtProps))
                .withIssuer(jwtProps.issuer())
                .build();
    }
}
