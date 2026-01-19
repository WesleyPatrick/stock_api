package io.github.WesleyPatrick.stock_api.application.usecase.auth.impl;

import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginRequest;
import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginResponse;
import io.github.WesleyPatrick.stock_api.application.mapper.auth.LoginMapper;
import io.github.WesleyPatrick.stock_api.application.usecase.auth.LoginUseCase;
import io.github.WesleyPatrick.stock_api.domain.model.User;
import io.github.WesleyPatrick.stock_api.infra.config.props.JwtProps;
import io.github.WesleyPatrick.stock_api.web.security.JwtService;
import io.github.WesleyPatrick.stock_api.web.security.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCaseImpl implements LoginUseCase {

    private AuthenticationManager am;
    private JwtService jwtService;
    private JwtProps props;
    private LoginMapper loginMapper;
    private RefreshTokenService refreshTokenService;

    public LoginUseCaseImpl(AuthenticationManager am,  JwtService jwtService, JwtProps props, LoginMapper loginMapper, RefreshTokenService refreshTokenService) {
        this.am = am;
        this.jwtService = jwtService;
        this.props = props;
        this.loginMapper = loginMapper;
        this.refreshTokenService = refreshTokenService;

    }

    @Override
    public LoginResponse execute(LoginRequest loginRequest) {

        System.out.println(loginRequest);

        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                loginRequest.email(),
                loginRequest.password()
        );

        Authentication auth = am.authenticate(upat);
        System.out.println(auth.getName());

        User user = (User) auth.getPrincipal();

        String token = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        refreshTokenService.upsert(user, newRefreshToken);



        return loginMapper.toLoginResponse(token, props.accessMinutes(), newRefreshToken);
    }
}
