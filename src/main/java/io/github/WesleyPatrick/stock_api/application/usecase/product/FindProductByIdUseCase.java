package io.github.WesleyPatrick.stock_api.application.usecase.product;

import io.github.WesleyPatrick.stock_api.application.dto.product.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface FindProductByIdUseCase {

    ProductResponse execute(UUID uuid);
}
