package io.github.WesleyPatrick.stock_api.web.handler;

import io.github.WesleyPatrick.stock_api.application.dto.error.ApiError;
import io.github.WesleyPatrick.stock_api.application.dto.error.ApiValidationError;
import io.github.WesleyPatrick.stock_api.domain.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<String> fieldErrors = ex.getBindingResult().
                getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiValidationError(
                        400,
                        "Validation Error",
                        fieldErrors
                )
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiError(401, "Não autorizado"));
        }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBadJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new ApiError(400, "JSON inválido"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(401, "E-mail ou senha inválidos"));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiError> handleDisabled() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(403, "Usuário desativado"));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiError> handleLocked() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(403, "Conta bloqueada"));
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity<ApiError> handleAccountExpired() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(403, "Conta expirada"));
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ApiError> handleCredentialsExpired() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(403, "Senha expirada"));
    }

}
