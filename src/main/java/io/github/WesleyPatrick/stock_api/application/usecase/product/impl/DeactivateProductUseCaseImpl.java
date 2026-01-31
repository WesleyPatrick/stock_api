package io.github.WesleyPatrick.stock_api.application.usecase.product.impl;

import io.github.WesleyPatrick.stock_api.application.usecase.product.DeactivateProductUseCase;
import io.github.WesleyPatrick.stock_api.application.usecase.product.DeleteProductUseCase;
import io.github.WesleyPatrick.stock_api.domain.exception.BusinessException;
import io.github.WesleyPatrick.stock_api.domain.exception.NotFoundException;
import io.github.WesleyPatrick.stock_api.domain.model.Product;
import io.github.WesleyPatrick.stock_api.infra.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeactivateProductUseCaseImpl implements DeactivateProductUseCase {

    private ProductRepository productRepository;

    public DeactivateProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));

        if (!product.isActive()) {
            throw new BusinessException("Product already inactive");
        }

        product.setActive(false);
        productRepository.save(product);

    }
}
