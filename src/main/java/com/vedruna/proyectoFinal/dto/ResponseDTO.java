package com.vedruna.proyectoFinal.dto;

/**
 * Clase genérica que representa una respuesta de la API, incluyendo un mensaje
 * y los datos correspondientes.
 *
 * @param <T> tipo de los datos que se incluirán en la respuesta
 */
public class ResponseDTO<T> {

    private String message;
    private T data;

    public ResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
