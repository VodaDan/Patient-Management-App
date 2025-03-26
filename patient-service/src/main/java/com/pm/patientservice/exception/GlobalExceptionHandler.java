package com.pm.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
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

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity <Map<String,String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){

        log.warn(ex.getMessage());
        Map<String,String> errors = new HashMap<>();
        errors.put("Message",ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFoundException(PatientNotFoundException ex){
        log.warn(ex.getMessage());
        Map<String,String> errors = new HashMap<>();

        errors.put("Message:",ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
