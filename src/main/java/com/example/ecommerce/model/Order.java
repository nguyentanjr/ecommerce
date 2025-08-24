package com.example.ecommerce.model;

import com.example.ecommerce.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order entity using Static Factory Methods + Lombok annotations
 */
@Entity
@Table(name = "orders")
@Getter @Setter                                    // ✨ Lombok: Generate getters/setters
@NoArgsConstructor                                 // ✨ Required by JPA  
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // ✨ Only use ID for equals/hashCode (JPA best practice)
@ToString(exclude = {"user", "orderItems"})       // ✨ Exclude lazy collections
@Accessors(chain = true)                          // ✨ Enable method chaining
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Order status is required")
    private OrderStatus status = OrderStatus.PENDING;

    @NotNull(message = "Total amount is required")
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @NotBlank(message = "Shipping address is required")
    @Column(name = "shipping_address", columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "order_notes")
    private String orderNotes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED,
        REFUNDED
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Business methods
    public BigDecimal calculateTotalAmount() {
        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalItems() {
        return orderItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
    }

    public void addOrderItem(Product product, int quantity, BigDecimal unitPrice) {
        if()
        orderItems.add(orderItem);
    }

    public boolean canBeCancelled() {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }

    public void cancel() {

    }

    // ✨ Business methods - ONLY methods actually used in OrderService
    
    public boolean isPending() {
    }

    public boolean isCompleted() {
    }

    public boolean isActive() {
    }

    public boolean hasItems() {
    }

    // ✨ Lombok generates all other getters/setters automatically!
}
