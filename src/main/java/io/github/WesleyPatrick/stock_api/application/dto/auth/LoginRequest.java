package io.github.WesleyPatrick.stock_api.application.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(

        @NotEmpty(message = "Email é obrigatório")
        @Schema(example = "wesley@email.com")
        String email,

        @NotEmpty(message = "Senha é obrigatória")
        @Schema(example = "123456")
        String password
) {
}
