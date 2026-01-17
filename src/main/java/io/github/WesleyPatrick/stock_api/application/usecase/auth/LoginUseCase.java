package io.github.WesleyPatrick.stock_api.application.usecase.auth;

import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginRequest;
import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginUseCase {
    public LoginResponse execute(LoginRequest loginRequest);
}
