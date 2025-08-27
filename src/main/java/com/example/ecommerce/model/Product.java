package com.example.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.ecommerce.exception.BadRequestException;

/**
 * Product entity using Static Factory Methods + Lombok annotations
 * Static factory methods provide descriptive ways to create different types of products
 */
@Entity
@Table(name = "products")
@Getter @Setter                                    // ✨ Lombok: Generate getters/setters
@NoArgsConstructor                                 // ✨ Required by JPA
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // ✨ Only use ID for equals/hashCode (JPA best practice)
@ToString(exclude = {"cartItems", "orderItems"})  // ✨ Exclude lazy collections
@Accessors(chain = true)                          // ✨ Enable method chaining
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    @Column(name = "image_url")
    private String imageUrl;

    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category is required")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<com.ecommerce.model.CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ✨ Static Factory Methods - ONLY methods actually used in the codebase

    /** Create a basic product - USED in ProductService */
    public static Product create(String name, String description, BigDecimal price,
                                 Integer stockQuantity, Category category) {
        Product product = new Product();
        return product.setName(name)
                .setDescription(description)
                .setPrice(price)
                .setStockQuantity(stockQuantity)
                .setCategory(category)
                .setActive(true);
    }

    /** Create a product with image - USED in DataInitializer */
    public static Product createWithImage(String name, String description, BigDecimal price,
                                          Integer stockQuantity, String imageUrl, Category category) {
        Product product = new Product();
        return product.setName(name)
                .setDescription(description)
                .setPrice(price)
                .setStockQuantity(stockQuantity)
                .setImageUrl(imageUrl)
                .setCategory(category)
                .setActive(true);
    }

    // ✨ Business methods - USED in CartService/OrderService

    public Product addStock(int quantity) {
        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        return this.setStockQuantity(this.stockQuantity + quantity);
    }

    public Product reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        if (quantity > stockQuantity) {
            throw new InsufficientStockException(this.name, quantity, this.stockQuantity);
        }
        return this.setStockQuantity(this.stockQuantity - quantity);
    }

    // ✨ Query methods - USED in business logic

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean hasStock(int quantity) {
        if (quantity < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }
        return stockQuantity >= quantity;
    }

    // ✨ Lombok generates all standard getters/setters, equals/hashCode, toString automatically!
}