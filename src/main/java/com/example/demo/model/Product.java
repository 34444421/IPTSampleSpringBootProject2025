package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_sku", columnList = "sku", unique = true),
        @Index(name = "idx_product_name", columnList = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)


public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "{validation.product.name.mandatory}")
    @Size(max = 100, message = "{validation.product.name.size}")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "{validation.product.description.size}")
    @Column(length = 500)
    private String description;

    @NotBlank(message = "{validation.product.sku.mandatory}")
    @Size(max = 50, message = "{validation.product.sku.size}")
    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @NotNull(message = "{validation.product.price.mandatory}")
    @PositiveOrZero(message = "{validation.product.price.positive}")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "{validation.product.stock.mandatory}")
    @PositiveOrZero(message = "{validation.product.stock.positive}")
    @Column(nullable = false)
    private Integer stockQuantity;

    @Size(max = 100, message = "{validation.product.category.size}")
    @Column(length = 100)
    private String category;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private boolean deleted = Boolean.FALSE;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}



