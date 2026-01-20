package io.github.WesleyPatrick.stock_api.application.mapper.product;

import io.github.WesleyPatrick.stock_api.application.dto.product.CreateProductRequest;
import io.github.WesleyPatrick.stock_api.application.dto.product.ProductResponse;
import io.github.WesleyPatrick.stock_api.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getMinimumQuantity(),
                product.isActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public Product toProduct(CreateProductRequest request, boolean active) {
        Product product = new Product();
        product.setName(request.name());
        product.setActive(active);
        product.setDescription(request.description());
        product.setQuantity(request.quantity());
        product.setMinimumQuantity(request.minimumQuantity());
        return product;
    }
}


