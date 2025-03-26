package com.pm.patientservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors used in PatientRequestDTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex){

        // Stores field validation errors
        Map<String,String> errors = new HashMap<>();

        // Loops through all validation errors and adds them to the map
        ex.getBindingResult().getFieldErrors().forEach(error-> errors.put(error.getField(),error.getDefaultMessage()));

        // Returns a 400 Bad Request response with validation messages
        return ResponseEntity.badRequest().body(errors);
    }
}
