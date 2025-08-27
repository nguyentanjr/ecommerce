package com.example.ecommerce.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Generic API response wrapper using Static Factory Method pattern
 * Private constructor enforces use of descriptive static factory methods
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter                          // ✨ Lombok: Generate getters
@ToString                        // ✨ Lombok: Generate toString
@EqualsAndHashCode              // ✨ Lombok: Generate equals/hashCode
@Accessors(chain = true)        // ✨ Enable method chaining
public class ApiResponse<T> {
    private final boolean success;           // ✨ Final fields for immutability
    private final String message;
    private final T data;
    private final String timestamp;
    private final Integer status;

    // ✨ Private constructor enforces Static Factory Method pattern
    private ApiResponse(boolean success, String message, T data, Integer status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = LocalDateTime.now().toString();
    }

    // ✨ Static Factory Methods with descriptive names (instead of constructors)

    /** Create successful response with data */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null, data, HttpStatus.OK.value());
    }

    /** Create successful response with data and message */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, HttpStatus.OK.value());
    }

    /** Create successful creation response */
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(true, "Resource created successfully", data,
                HttpStatus.CREATED.value();
    }

    /** Create successful creation response with custom message */
    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(true, message, data, HttpStatus.CREATED.value());
    }

    /** Create no content response */
    public static ApiResponse<Void> noContent() {
        return new ApiResponse<>(true, "Operation completed successfully", null,
                HttpStatus.NO_CONTENT.value());
    }

    /** Create no content response with custom message */
    public static ApiResponse<Void> noContent(String message) {
        return new ApiResponse<>(true, message, null, HttpStatus.NO_CONTENT.value());
    }

    /** Create error response */
    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(false, message, null, status.value());
    }

    /** Create bad request error */
    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.BAD_REQUEST.value());
    }

    /** Create not found error */
    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.NOT_FOUND.value());
    }

    /** Create unauthorized error */
    public static <T> ApiResponse<T> unauthorized(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.UNAUTHORIZED.value());
    }

    /** Create forbidden error */
    public static <T> ApiResponse<T> forbidden(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.FORBIDDEN.value());
    }

    // ✨ Builder-style methods for adding metadata (returns new immutable instance)


    // ✨ Lombok generates all getters automatically - no boilerplate needed!
}
