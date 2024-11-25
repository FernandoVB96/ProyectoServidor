package com.vedruna.proyectoFinal.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.vedruna.proyectoFinal.dto.ResponseDTO;

@RestControllerAdvice
public class ExceptionController {

    // Maneja excepciones de tipo IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Respuesta con código 400 (Bad Request)
        ResponseDTO<String> response = new ResponseDTO<>("Bad Request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
    }

    // Maneja excepciones generales (no específicas)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleGeneralException(Exception ex) {
        // Respuesta con código 500 (Internal Server Error)
        ResponseDTO<String> response = new ResponseDTO<>("Internal Server Error", "An unexpected error occurred. Check that the path is correct and does not exist in the database and try again.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
