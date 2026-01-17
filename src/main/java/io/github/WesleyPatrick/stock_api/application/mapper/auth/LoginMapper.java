package io.github.WesleyPatrick.stock_api.application.mapper.auth;

import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginResponse;
import io.github.WesleyPatrick.stock_api.infra.config.props.JwtProps;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

    private static final long SIXTY_SECONS = 60;

    public LoginResponse toLoginResponse(String token, Long accessMinutes) {
        return new LoginResponse(
                token,
                accessMinutes / SIXTY_SECONS + " min");
    }
}
