package com.example.ecommerce.test;

import com.example.ecommerce.dto.error.ErrorResponse;
import com.example.ecommerce.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleIllegalArgumentException() {
        // Giả lập WebRequest
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/test");

        // Tạo exception
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException("Invalid input");

        // Gọi handler
        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentNotValidException(ex, request);

        // Kiểm tra kết quả
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Invalid input", response.getBody().getMessage());
        assertEquals("BAD_REQUEST", "BAD");
    }
}
