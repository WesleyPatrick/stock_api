package io.github.WesleyPatrick.stock_api.application.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para criação de um produto")
public record CreateProductRequest(

        @NotBlank
        @Schema(example = "Macbook")
        String name,

        @NotBlank
        @Schema(example = "Macbook Pro m4")
        String description,

        @NotNull
        @Min(0)
        @Schema(example = "100")
        Integer quantity,

        @NotNull
        @Min(0)
        @Schema(example = "10")
        Integer minimumQuantity
) {}
