package io.github.WesleyPatrick.stock_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, name = "minimum_quantity")
    private Integer minimumQuantity;

    @Column(nullable = false)
    private boolean active;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    public Product() {}

    public Product(String description, Integer minumun_quantity, String name, Integer quantity) {
        this.description = description;
        this.active = true;
        this.minimumQuantity = minumun_quantity;
        this.name = name;
        this.quantity = quantity;
    }

    public void addStock(Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount must be greater than 0.");
        }

        if (amount < this.minimumQuantity) {
            throw new IllegalArgumentException("The amount must be greater than or equal to the minimum quantity.");
        }

        this.quantity += amount;
    }
    public void removeStock(Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount must be greater than 0.");
        }

        if (amount > this.quantity) {
            throw new IllegalArgumentException("The amount must be less than or equal to the quantity.");
        }

        this.quantity -= amount;
    }


}
