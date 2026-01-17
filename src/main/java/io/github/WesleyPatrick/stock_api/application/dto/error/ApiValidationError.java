package io.github.WesleyPatrick.stock_api.application.dto.error;

import java.util.List;

public record ApiValidationError <T>(
        int code,
        String message,
        List<T> errors



) {
}
