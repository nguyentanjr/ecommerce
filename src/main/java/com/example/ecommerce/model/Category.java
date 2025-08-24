package com.example.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Category entity using Static Factory Methods + Lombok annotations
 * Static factory methods provide descriptive ways to create categories
 */
@Entity
@Table(name = "categories")
@Getter @Setter                                    // ✨ Lombok: Generate getters/setters
@AllArgsConstructor
@NoArgsConstructor                                 // ✨ Required by JPA
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // ✨ Only use ID for equals/hashCode (JPA best practice)
@ToString(exclude = {"products"})                 // ✨ Exclude lazy collections
@Accessors(chain = true)                          // ✨ Enable method chaining
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    @Column(unique = true)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    // ✨ Static Factory Methods - ONLY what's actually needed

    /** Create category - USED in DataInitializer */
    public static Category create(String name, String description) {
        return new Category()
                .setName(name)
                .setDescription(description);
    }

    // ✨ Business methods - USED in business logic

    public boolean hasProducts() {
        return products != null && !products.isEmpty();
    }

    public int getProductCount() {
        return products != null ? products.size() : 0;
    }

    // ✨ Lombok generates all standard getters/setters, equals/hashCode, toString automatically!
}
