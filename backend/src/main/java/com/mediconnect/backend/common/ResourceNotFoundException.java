package com.mediconnect.backend.common;

/**
 * Thrown when a requested entity cannot be found in the database.
 * Handled by {@link GlobalExceptionHandler} and mapped to HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
