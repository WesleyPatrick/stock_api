package io.github.WesleyPatrick.stock_api.application.usecase.auth;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface LogoutUseCase {

    public void execute(Authentication auth);
}
