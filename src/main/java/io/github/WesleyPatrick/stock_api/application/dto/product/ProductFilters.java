package io.github.WesleyPatrick.stock_api.application.dto.product;

import java.time.LocalDateTime;

public record ProductFilters(
        String name,
        Boolean active,
        LocalDateTime createdFrom,
        LocalDateTime createdTo
) {
}

