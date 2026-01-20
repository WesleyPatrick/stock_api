package io.github.WesleyPatrick.stock_api.application.usecase.product;

import io.github.WesleyPatrick.stock_api.application.dto.product.CreateProductRequest;
import io.github.WesleyPatrick.stock_api.application.dto.product.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public interface CreateProductUseCase {

    ProductResponse execute(CreateProductRequest request);

}
