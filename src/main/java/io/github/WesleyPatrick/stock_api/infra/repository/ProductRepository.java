package io.github.WesleyPatrick.stock_api.infra.repository;

import io.github.WesleyPatrick.stock_api.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);
}
