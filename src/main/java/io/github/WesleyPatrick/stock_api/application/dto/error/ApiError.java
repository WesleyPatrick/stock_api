package io.github.WesleyPatrick.stock_api.application.dto.error;

public record ApiError (
        int status,
        String message
) { }
