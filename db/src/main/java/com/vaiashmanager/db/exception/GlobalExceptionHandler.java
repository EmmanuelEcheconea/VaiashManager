package com.vaiashmanager.db.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(CustomExceptionHandler.class)
    public ResponseEntity<Object> handleException(CustomExceptionHandler ex) {
        // Crear el cuerpo de la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage()); // El mensaje de la excepción
        response.put("status", ex.getStatus()); // El código de estado HTTP
        response.put("date", LocalDateTime.now().toString()); // Fecha y hora actual en formato ISO

        // Retornar el ResponseEntity con el status y el cuerpo en formato JSON
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
