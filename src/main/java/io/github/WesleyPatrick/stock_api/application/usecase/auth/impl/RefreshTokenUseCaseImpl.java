package io.github.WesleyPatrick.stock_api.application.usecase.auth.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginResponse;
import io.github.WesleyPatrick.stock_api.application.dto.auth.RefreshRequest;
import io.github.WesleyPatrick.stock_api.application.mapper.auth.LoginMapper;
import io.github.WesleyPatrick.stock_api.application.usecase.auth.RefreshTokenUseCase;
import io.github.WesleyPatrick.stock_api.domain.exception.UnauthorizedException;
import io.github.WesleyPatrick.stock_api.domain.model.RefreshToken;
import io.github.WesleyPatrick.stock_api.domain.model.User;
import io.github.WesleyPatrick.stock_api.infra.config.props.JwtProps;
import io.github.WesleyPatrick.stock_api.infra.repository.RefreshTokenRepository;
import io.github.WesleyPatrick.stock_api.web.security.JwtService;
import io.github.WesleyPatrick.stock_api.web.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private RefreshTokenRepository refreshTokenRepository;
    private JwtService jwtService;
    private RefreshTokenService refreshTokenService;
    private JwtProps jwtProps;
    private LoginMapper loginMapper;

    public RefreshTokenUseCaseImpl(RefreshTokenRepository refreshTokenRepository, JwtService jwtService, JwtProps jwtProps, LoginMapper loginMapper,  RefreshTokenService refreshTokenService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.jwtProps = jwtProps;
        this.loginMapper = loginMapper;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public LoginResponse execute(RefreshRequest refreshRequest) {

        DecodedJWT decoded = jwtService.verifyToken(refreshRequest.refreshToken());

        if (!jwtService.isRefreshToken(decoded)) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        User user = refreshTokenService.getUserFromToken(refreshRequest.refreshToken());

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        refreshTokenService.upsert(user, newRefreshToken);

        return loginMapper.toLoginResponse(newAccessToken, jwtProps.accessMinutes(), newRefreshToken);
    }
}
