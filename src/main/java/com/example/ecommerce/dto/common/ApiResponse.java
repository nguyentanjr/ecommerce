package com.example.ecommerce.dto.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final Integer status;
    private final String timestamp;

    /** Create successful response with data, CODE 200, use when successful handle a request*/
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true,null,data, HttpStatus.OK.value(), null);
    }

    /** Create successful response with data and message, CODE 200*/
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true,message,data,HttpStatus.OK.value(), null);
    }

    /** Create successful creation response, CODE 201*/
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(true,"Resource created successfully", data,
                HttpStatus.CREATED.value(),null);
    }

    /** Create successful creation response with custom message*/
    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(true, message, data, HttpStatus.CREATED.value(),  null);
    }

    /** Create no content response */
    public static ApiResponse<Void> noContent() {
        return new ApiResponse<>(true, "Operation completed successfully", null,
                HttpStatus.NO_CONTENT.value(),  null);
    }

    /** Create no content response with custom message */
    public static ApiResponse<Void> noContent(String message) {
        return new ApiResponse<>(true, message, null, HttpStatus.NO_CONTENT.value(),  null);
    }

    /** Create error response */
    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(false, message, null, status.value(),  null);
    }

    /** Create bad request error */
    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.BAD_REQUEST.value(),  null);
    }

    /** Create not found error */
    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.NOT_FOUND.value(),  null);
    }

    /** Create unauthorized error */
    public static <T> ApiResponse<T> unauthorized(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.UNAUTHORIZED.value(),  null);
    }

    /** Create forbidden error */
    public static <T> ApiResponse<T> forbidden(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.FORBIDDEN.value(),  null);
    }


}
