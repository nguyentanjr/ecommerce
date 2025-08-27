package com.example.ecommerce.exception;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException{

    private String resourceName;
    private String field;
    private Object value;

    public ResourceNotFoundException(String resourceName, String field, Object value) {
        super(String.format("%s not found with %s : '%s'", resourceName, field, value),
                HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
        this.resourceName = resourceName;
        this.field = field;
        this.value = value;
    }

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }

    public String getResourceName() { return resourceName; }
    public String getField() { return field; }
    public Object getValue() { return value; }


}
