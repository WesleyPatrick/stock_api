package io.github.WesleyPatrick.stock_api.application.usecase.product;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface DeactivateProductUseCase {
    void execute(UUID id);
}
