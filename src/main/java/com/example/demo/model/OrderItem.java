package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "{validation.orderItem.quantity.mandatory}")
    @Positive(message = "{validation.orderItem.quantity.positive}")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "{validation.orderItem.price.mandatory}")
    @Positive(message = "{validation.orderItem.price.positive}")
    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    @Size(max = 200, message = "{validation.orderItem.notes.size}")
    @Column(length = 200)
    private String notes;
}

