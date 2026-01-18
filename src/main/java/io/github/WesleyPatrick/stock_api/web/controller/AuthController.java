package io.github.WesleyPatrick.stock_api.web.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginRequest;
import io.github.WesleyPatrick.stock_api.application.dto.auth.LoginResponse;
import io.github.WesleyPatrick.stock_api.application.dto.auth.RefreshRequest;
import io.github.WesleyPatrick.stock_api.application.usecase.auth.LoginUseCase;
import io.github.WesleyPatrick.stock_api.application.usecase.auth.RefreshTokenUseCase;
import io.github.WesleyPatrick.stock_api.web.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    LoginUseCase loginUseCase;
    @Autowired
    RefreshTokenUseCase refreshTokenUseCase;


    @Operation(summary = "Login do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class)
            )),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content())
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok(loginUseCase.execute(loginRequest));

    }

    @Operation(summary = "Gera novo access token usando refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token renovado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class)
            )),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido", content = @Content())
    })
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody @Valid RefreshRequest refreshRequest){
        return ResponseEntity.ok(refreshTokenUseCase.execute(refreshRequest));
    }

}
