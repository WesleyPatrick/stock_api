package io.github.WesleyPatrick.stock_api.application.usecase.product;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface DeleteProductUseCase {
    void execute(UUID id);
}

