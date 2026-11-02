package com.example.reservations.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Recurso inexistente: 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(
            ResourceNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "Not Found",
                        "message", exception.getMessage()
                ));
    }

    // Email duplicado: 409
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(
            EmailAlreadyExistsException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "Conflict",
                        "message", exception.getMessage()
                ));
    }

    // Datos de reserva incorrectos: 400
    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<Map<String, String>> handleInvalidReservation(
            InvalidReservationException exception) {

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "error", "Bad Request",
                        "message", exception.getMessage()
                ));
    }

    // Referencias inexistentes: 404
    @ExceptionHandler(InvalidReferenceException.class)
    public ResponseEntity<Map<String, String>> handleInvalidReference(
            InvalidReferenceException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "Not Found",
                        "message", exception.getMessage()
                ));
    }

    // Errores de validación de DTO: 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    // Errores de integridad de base de datos: 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(
            DataIntegrityViolationException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "Conflict",
                        "message", "Los datos entran en conflicto con un registro existente."
                ));
    }
}