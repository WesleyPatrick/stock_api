package io.github.WesleyPatrick.stock_api.infra.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProps (
    String issuer,
    String secret,
    long accessMinutes
) {}
