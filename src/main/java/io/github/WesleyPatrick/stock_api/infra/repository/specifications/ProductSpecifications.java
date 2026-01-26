package io.github.WesleyPatrick.stock_api.infra.repository.specifications;

import io.github.WesleyPatrick.stock_api.application.dto.product.ProductFilters;
import io.github.WesleyPatrick.stock_api.domain.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public final class ProductSpecifications {

    private ProductSpecifications() {}

    public static Specification<Product> withFilters(ProductFilters f) {
        return (root, query, cb) -> {
            Predicate p = cb.conjunction();

            if (f.name() != null) {
                p = cb.and(p,
                        cb.like(cb.lower(root.get("name")),
                                "%" + f.name().toLowerCase() + "%"));
            }

            if (f.active() != null) {
                p = cb.and(p, cb.equal(root.get("active"), f.active()));
            }

            if (f.createdFrom() != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(
                        root.get("createdAt"), f.createdFrom()));
            }

            if (f.createdTo() != null) {
                p = cb.and(p, cb.lessThanOrEqualTo(
                        root.get("createdAt"), f.createdTo()));
            }

            return p;
        };
    }
}