package com.example.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * OrderItem entity using Static Factory Methods + Lombok annotations
 */
@Entity
@Table(name = "order_items")
@Getter @Setter                                    // ✨ Lombok: Generate getters/setters
@AllArgsConstructor
@NoArgsConstructor                                 // ✨ Required by JPA
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // ✨ Only use ID for equals/hashCode (JPA best practice)
@ToString(exclude = {"order", "product"})         // ✨ Exclude lazy collections
@Accessors(chain = true)                          // ✨ Enable method chaining
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Business methods
    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // ✨ Static Factory Methods with descriptive names (moved from OrderItemFactory)

    /** Create OrderItem from CartItem */
    public static OrderItem fromCartItem(Order order, com.ecommerce.model.CartItem cartItem) {
        return new OrderItem()
                .setOrder(order)
                .setProduct(cartItem.getProduct())
                .setQuantity(cartItem.getQuantity())
                .setUnitPrice(cartItem.getProduct().getPrice());
    }

    /** Create OrderItem with specific details */
    public static OrderItem create(Order order, Product product, Integer quantity) {
        return new OrderItem()
                .setOrder(order)
                .setProduct(product)
                .setQuantity(quantity)
                .setUnitPrice(product.getPrice());
    }

    /** Create OrderItem with custom price (for discounts) */
    public static OrderItem createWithPrice(Order order, Product product, Integer quantity, BigDecimal customPrice) {
        return new OrderItem()
                .setOrder(order)
                .setProduct(product)
                .setQuantity(quantity)
                .setUnitPrice(customPrice);
    }

    /** Create OrderItem for gift (special handling) */
    public static OrderItem createGift(Order order, Product product, Integer quantity, String giftMessage) {
        // In a real implementation, you might have a giftMessage field
        return new OrderItem()
                .setOrder(order)
                .setProduct(product)
                .setQuantity(quantity)
                .setUnitPrice(product.getPrice());
    }

    // ✨ Business methods using method chaining

    public OrderItem updateQuantity(Integer newQuantity) {
        return this.setQuantity(newQuantity);
    }

    public OrderItem applyDiscount(BigDecimal discountedPrice) {
        return this.setUnitPrice(discountedPrice);
    }

    // ✨ Query methods

    public boolean isDiscounted() {
        return unitPrice.compareTo(product.getPrice()) < 0;
    }

    public BigDecimal getSavings() {
        if (isDiscounted()) {
            return product.getPrice().subtract(unitPrice).multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }

    public boolean isLargeQuantity() {
        return quantity >= 10;  // Business rule: 10+ items is "large"
    }

    // ✨ Lombok generates all other getters/setters automatically!
}
