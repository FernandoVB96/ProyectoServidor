package com.vedruna.proyectoFinal.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vedruna.proyectoFinal.dto.ResponseDTO;

/**
 * Controlador para manejar las excepciones en la aplicación.
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * Maneja excepciones de tipo IllegalArgumentException.
     * 
     * @param ex la excepción lanzada
     * @return ResponseEntity con un mensaje de error y estado HTTP 400 (Bad Request)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseDTO<String> response = new ResponseDTO<>("Solicitud incorrecta", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Maneja excepciones generales no específicas.
     * 
     * @param ex la excepción lanzada
     * @return ResponseEntity con un mensaje de error y estado HTTP 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleGeneralException(Exception ex) {
        ResponseDTO<String> response = new ResponseDTO<>("Error interno del servidor", 
            "Ocurrió un error inesperado. Verifica que la ruta sea correcta y que exista en la base de datos, luego intenta nuevamente.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
