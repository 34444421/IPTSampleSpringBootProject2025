package com.example.demo.model;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name="customer",indexes = {
        @Index(name = "idx_customer_email", columnList = "email", unique = true),
        @Index(name = "idx_customer_phone", columnList = "phone")
}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"password", "address"})
@SQLDelete(sql = "UPDATE customers SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "{validation.customer.name.mandatory}")
    @Size(max = 100, message = "{validation.customer.name.size}")
    @Column(nullable = false, length = 100)
    private String name;

    @Email(message = "{validation.customer.email.invalid}")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 60) // BCrypt hash length
    private String password;

    @Size(max = 20, message = "{validation.customer.phone.size}")
    @Column(length = 20)
    private String phone;

    @Embedded
    @Valid // Ensures Address validations are checked
    private Address address;

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