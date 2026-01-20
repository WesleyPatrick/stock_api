package io.github.WesleyPatrick.stock_api.application.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados de retorno do produto")
public record ProductResponse(

        @Schema(example = "77a8a400-7776-48fc-a5ab-76128a82c510")
        UUID id,

        @Schema(example = "Macbook")
        String name,

        @Schema(example = "Macbook Pro m4")
        String description,

        @Schema(example = "100")
        Integer quantity,

        @Schema(example = "10")
        Integer minimumQuantity,

        @Schema(example = "true")
        boolean active,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
