package io.github.WesleyPatrick.stock_api.application.dto.auth;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,

        @Schema(example = "900")
        String expiresIn,

        @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
        String refreshToken
) {
}
