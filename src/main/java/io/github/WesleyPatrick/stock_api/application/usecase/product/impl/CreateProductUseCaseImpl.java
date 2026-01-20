package io.github.WesleyPatrick.stock_api.application.usecase.product.impl;

import io.github.WesleyPatrick.stock_api.application.dto.product.CreateProductRequest;
import io.github.WesleyPatrick.stock_api.application.dto.product.ProductResponse;
import io.github.WesleyPatrick.stock_api.application.mapper.product.ProductMapper;
import io.github.WesleyPatrick.stock_api.application.usecase.product.CreateProductUseCase;
import io.github.WesleyPatrick.stock_api.domain.exception.DuplicateResourceException;
import io.github.WesleyPatrick.stock_api.domain.model.Product;
import io.github.WesleyPatrick.stock_api.infra.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public CreateProductUseCaseImpl(ProductRepository productRepository,  ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;

    }

    @Override
    public ProductResponse execute(CreateProductRequest request) {

        if (productRepository.existsByName(request.name())) {
            throw  new DuplicateResourceException("Product with name " + request.name() + " already exists");
        }
        Product product = productRepository.save(productMapper.toProduct(request, true));

        return productMapper.toProductResponse(product);
    }
}
