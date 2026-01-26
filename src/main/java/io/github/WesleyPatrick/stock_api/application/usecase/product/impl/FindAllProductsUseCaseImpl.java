package io.github.WesleyPatrick.stock_api.application.usecase.product.impl;

import io.github.WesleyPatrick.stock_api.application.dto.page.PageResponse;
import io.github.WesleyPatrick.stock_api.application.dto.product.ProductFilters;
import io.github.WesleyPatrick.stock_api.application.dto.product.ProductResponse;
import io.github.WesleyPatrick.stock_api.application.mapper.product.ProductMapper;
import io.github.WesleyPatrick.stock_api.application.usecase.product.FindAllProductsUseCase;
import io.github.WesleyPatrick.stock_api.domain.model.Product;
import io.github.WesleyPatrick.stock_api.infra.repository.ProductRepository;
import io.github.WesleyPatrick.stock_api.infra.repository.specifications.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FindAllProductsUseCaseImpl implements FindAllProductsUseCase {

    private ProductRepository productRepository;
    private ProductMapper mapper;

    public FindAllProductsUseCaseImpl(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<ProductResponse> execute(ProductFilters filters, Pageable pageable) {
        Specification<Product> specs = ProductSpecifications.withFilters(filters);

        Page<Product> productsPage = productRepository.findAll(specs, pageable);
        Page<ProductResponse> mapped = productsPage.map(mapper::toProductResponse);

        return new PageResponse<>(
                mapped.getContent(),
                mapped.getTotalElements(),
                mapped.getTotalPages(),
                mapped.getNumber(),
                mapped.getSize(),
                mapped.isFirst(),
                mapped.isLast()
        );

    }
}
