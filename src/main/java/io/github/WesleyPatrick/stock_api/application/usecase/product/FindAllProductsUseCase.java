package io.github.WesleyPatrick.stock_api.application.usecase.product;

import io.github.WesleyPatrick.stock_api.application.dto.page.PageResponse;
import io.github.WesleyPatrick.stock_api.application.dto.product.ProductFilters;
import io.github.WesleyPatrick.stock_api.application.dto.product.ProductResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface FindAllProductsUseCase {
    PageResponse<ProductResponse> execute(ProductFilters filters, Pageable pageable);
}
