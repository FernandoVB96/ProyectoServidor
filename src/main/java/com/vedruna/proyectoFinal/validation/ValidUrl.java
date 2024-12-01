package com.vedruna.proyectoFinal.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación personalizada para validar el formato de una URL.
 * Esta anotación se asocia con el validador UrlValidator, que garantiza que el valor de un campo
 * tenga el formato adecuado de una URL (http o https).
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UrlValidator.class)
public @interface ValidUrl {

    String message() default "Formato de URL inválido";

    /**
     * Permite agrupar validaciones. Este valor puede usarse para agrupar diferentes validaciones
     * bajo un mismo contexto.
     * 
     * @return Las clases de los grupos de validación.
     */
    Class<?>[] groups() default {};

    /**
     * Permite asociar información adicional a la validación, como detalles específicos para
     * el proceso de validación.
     * 
     * @return La carga útil asociada a la validación.
     */
    Class<? extends Payload>[] payload() default {};
}
