package io.github.WesleyPatrick.stock_api.application.usecase.product.impl;

import io.github.WesleyPatrick.stock_api.application.dto.product.ProductResponse;
import io.github.WesleyPatrick.stock_api.application.mapper.product.ProductMapper;
import io.github.WesleyPatrick.stock_api.application.usecase.product.FindProductByIdUseCase;
import io.github.WesleyPatrick.stock_api.domain.exception.NotFoundException;
import io.github.WesleyPatrick.stock_api.domain.model.Product;
import io.github.WesleyPatrick.stock_api.infra.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindProductByIdUseCaseImpl implements FindProductByIdUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public FindProductByIdUseCaseImpl(ProductRepository productRepository,  ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse execute(UUID uuid) {
        Product product = productRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return productMapper.toProductResponse(product);
    }
}
