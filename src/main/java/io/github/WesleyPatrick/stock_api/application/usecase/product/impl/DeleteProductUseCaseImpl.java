package io.github.WesleyPatrick.stock_api.application.usecase.product.impl;

import io.github.WesleyPatrick.stock_api.application.usecase.product.DeleteProductUseCase;
import io.github.WesleyPatrick.stock_api.domain.exception.NotFoundException;
import io.github.WesleyPatrick.stock_api.domain.model.Product;
import io.github.WesleyPatrick.stock_api.infra.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {

    private ProductRepository productRepository;

    public DeleteProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
