package io.github.WesleyPatrick.stock_api.application.usecase.auth.impl;

import io.github.WesleyPatrick.stock_api.application.usecase.auth.LogoutUseCase;
import io.github.WesleyPatrick.stock_api.infra.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LogoutUseCaseImpl implements LogoutUseCase {

    private RefreshTokenRepository refreshTokenRepository;

    public LogoutUseCaseImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void execute(Authentication auth) {

        String email = auth.getName();
        refreshTokenRepository.deleteByUser_Email(email);
    }
}
