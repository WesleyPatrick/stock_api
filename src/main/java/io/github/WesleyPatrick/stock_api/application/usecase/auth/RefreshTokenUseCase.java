package io.github.WesleyPatrick.stock_api.application.usecase.auth;

import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginRequest;
import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginResponse;
import io.github.WesleyPatrick.stock_api.application.dto.auth.RefreshRequest;
import io.github.WesleyPatrick.stock_api.domain.model.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenUseCase {
    public LoginResponse execute(RefreshRequest req);
}
