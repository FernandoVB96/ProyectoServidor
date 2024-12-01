package com.vedruna.proyectoFinal.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Validador personalizado para verificar si una cadena es una URL válida.
 * Se utiliza para asegurar que una URL esté en un formato adecuado (http o https).
 */
public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    /**
     * Expresión regular que define el formato válido de una URL (http o https).
     */
    private static final String URL_REGEX = "^(http|https)://[a-zA-Z0-9-_.]+(?:\\.[a-zA-Z]{2,})+(?:/[\\w-._~:/?#[\\]@!$&'()*+,;=.]+)*$";

    /**
     * Inicializa el validador. Este método no requiere ninguna configuración adicional en este caso.
     * 
     * @param constraintAnnotation La anotación asociada a este validador.
     */
    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }

    /**
     * Valida si el valor proporcionado cumple con el formato adecuado de una URL.
     * 
     * @param value El valor que se va a validar.
     * @param context El contexto de validación.
     * @return true si el valor es una URL válida, false en caso contrario.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return Pattern.matches(URL_REGEX, value);
    }
}
