package com.vedruna.proyectoFinal.validation;

// Importaciones necesarias para la validación y el uso de expresiones regulares.
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    // Expresión regular que define el formato válido de una URL (http o https).
    private static final String URL_REGEX = "^(http|https)://[a-zA-Z0-9-_.]+(?:\\.[a-zA-Z]{2,})+(?:/[\\w-._~:/?#[\\]@!$&'()*+,;=.]+)*$";

    // Método que inicializa el validador. No se necesita hacer nada en este caso, por eso está vacío.
    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }

    // Método que realiza la validación del formato de la URL.
    // Retorna true si la URL es válida según la expresión regular, o false si no lo es.
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Si la URL es nula o vacía, se considera válida (esto puede cambiar dependiendo de la lógica del negocio).
        if (value == null || value.isEmpty()) {
            return true;
        }
        // Usamos la expresión regular definida previamente para validar el formato de la URL.
        return Pattern.matches(URL_REGEX, value);
    }
}
