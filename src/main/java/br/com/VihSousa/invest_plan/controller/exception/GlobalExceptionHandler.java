package br.com.VihSousa.invest_plan.controller.exception;

import br.com.VihSousa.invest_plan.service.exception.InsufficientFundsException;
import br.com.VihSousa.invest_plan.service.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

// Global exception handler to manage exceptions across the whole application

@ControllerAdvice
public class GlobalExceptionHandler {

    // Treatment for Validation Errors (400)
    @SuppressWarnings("null") // supress warnings for potential null values in validation errors
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationError(org.springframework.web.bind.MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400
        
        // Gets the first error message from the list (e.g., "Password must be 8 characters")
        String errorMessage = "Validation Error";

        if (e.getBindingResult().getFieldError() != null) {
            errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        }
        
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Validation Error");
        err.setMessage(errorMessage); //The specific message from the @Size or @Email annotation
        err.setPath(request.getRequestURI());
        
        return ResponseEntity.status(status).body(err);
    }

    // Treatment for Resource Not Found (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(); // Create a StandardError object
        err.setTimestamp(Instant.now()); 
        err.setStatus(status.value());
        err.setError("Resource not found");
        err.setMessage(e.getMessage()); // Gets the message you defined in the Service
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Treatment for Data Conflict / Email (409)
    @ExceptionHandler(br.com.VihSousa.invest_plan.service.exception.CategoryAlreadyExistsException.class)
    public ResponseEntity<StandardError> categoryConflict(br.com.VihSousa.invest_plan.service.exception.CategoryAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Category Conflict");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }   

    // Treatment for Unprocessable Entity (422)
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<StandardError> insufficientFunds(InsufficientFundsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Insufficient Funds");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}